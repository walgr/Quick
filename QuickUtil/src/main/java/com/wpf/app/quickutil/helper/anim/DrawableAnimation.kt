package com.wpf.app.quickutil.helper.anim

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.os.Handler
import android.os.Looper
import android.view.ContextThemeWrapper
import android.view.View
import android.widget.ImageView
import androidx.annotation.IntegerRes
import com.wpf.app.quickutil.utils.LogUtil
import java.lang.ref.SoftReference


/**
 * Created by 王朋飞 on 2022/3/2.
 * 帧动画
 * 支持正序、逆序、中间开始
 */
class DrawableAnimation private constructor(mContext: Context) {
    @IntegerRes
    private var resId = -1 //图片资源
    private val mSoftReferenceContext: SoftReference<Context?> = SoftReference(mContext)
    private var frameDelay = 1000 / 60 //帧间延时 毫秒 60帧为 1000/60

    private fun setResId(resId: Int, frameDelay: Int) {
        this.resId = resId
        this.frameDelay = frameDelay
    }

    /**
     * 从xml中读取帧数组
     */
    private fun getData(resId: Int, isReverse: Boolean): IntArray? {
        if (mSoftReferenceContext.get() == null) return null
        val array = mSoftReferenceContext.get()!!.resources.obtainTypedArray(resId)

        val len = array.length()
        val intArray = IntArray(array.length())

        if (!isReverse) {
            for (i in 0 until len) {
                intArray[i] = array.getResourceId(i, 0)
            }
        } else {
            for (i in len - 1 downTo 0) {
                intArray[i] = array.getResourceId(i, 0)
            }
        }
        array.recycle()
        return intArray
    }

    /**
     * @param isReverse 是否反向 默认正向 false
     * @param isRepeat  是否重复 默认重复 true
     */
    fun createProgressDialogAnim(
        imageView: ImageView?,
        isReverse: Boolean = false,
        isRepeat: Boolean = true,
    ): FramesSequenceAnimation {
        return FramesSequenceAnimation(imageView, getData(resId, isReverse), frameDelay, isRepeat)
    }

    /**
     * 循环读取帧---循环播放帧
     */
    class FramesSequenceAnimation internal constructor(
        imageView: ImageView?, // 帧数组
        private val mFrames: IntArray?,
        private val mDelayMillis: Int,
        private val isRepeat: Boolean,
    ) {
        private var mIndex: Int // 当前帧
        private var mShouldRun: Boolean? = null // 开始/停止播放用
        private var mIsRunning: Boolean? = null // 动画是否正在播放，防止重复播放
        private val mSoftReferenceImageView: SoftReference<ImageView?> // 软引用ImageView，以便及时释放掉
        private val mHandler: Handler = Handler(Looper.myLooper()!!)
        private var mOnAnimationStoppedListener: OnAnimationStoppedListener? = null //播放停止监听

        private var mBitmap: Bitmap? = null
        private val mBitmapOptions: BitmapFactory.Options //Bitmap管理类，可有效减少Bitmap的OOM问题
        private var mRunnable: Runnable? = null
        private var curImageRes = -1
        private var unShownCount = 0 //未显示还继续线程次数

        init {
            mIndex = -1
            mSoftReferenceImageView = SoftReference(imageView)
            //帧动画时间间隔，毫秒

            if (mFrames != null && mFrames.isNotEmpty()) {
                imageView!!.setImageResource(mFrames[0])
            }

            // 当图片大小类型相同时进行复用，避免频繁GC
            var bmp: Bitmap? = null
            var width = 0
            var height = 0

            if (imageView != null && imageView.drawable != null) {
                if (imageView.drawable is BitmapDrawable) {
                    bmp = (imageView.drawable as BitmapDrawable).bitmap
                    width = bmp.width
                    height = bmp.height
                }
                //暂不支持VectorDrawable复用Bitmap
//                else {
//                    if (Build.VERSION.SDK_INT >= 21) {
//                        if (imageView.getDrawable() instanceof VectorDrawable) {
//                            VectorDrawable vectorDrawable = (VectorDrawable) imageView.getDrawable();
//                            bmp = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
//                            Canvas canvas = new Canvas(bmp);
//                            vectorDrawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
//                            vectorDrawable.draw(canvas);
//                            width = bmp.getWidth();
//                            height = bmp.getHeight();
//                        }
//                    }
//                }
            }
            if (bmp != null) {
                val config = bmp.config
                mBitmap = Bitmap.createBitmap(width, height, config)
            }
            mBitmapOptions = BitmapFactory.Options()
            //设置Bitmap内存复用
            if (mBitmap != null) {
                mBitmapOptions.inBitmap = mBitmap //Bitmap复用内存块，类似对象池，避免不必要的内存分配和回收
            }
            mBitmapOptions.inMutable = true //解码时返回可变Bitmap
            mBitmapOptions.inSampleSize = 1 //缩放比例
        }

        private val next: Int
            //循环读取下一帧
            get() {
                if (mFrames == null || mFrames.isEmpty()) return -1
                mIndex++
                if (mIndex >= mFrames.size) {
                    mIndex = if (isRepeat) {
                        0
                    } else {
                        mFrames.size - 1
                    }
                    if (!isRepeat) {
                        mShouldRun = false
                    }
                }
                if (mIndex >= 0 && mIndex < mFrames.size) {
                    return mFrames[mIndex]
                }
                return mFrames[0]
            }

        /**
         * 播放动画，同步锁防止多线程读帧时，数据安全问题
         */
        @Synchronized
        fun start() {
            if (mShouldRun != null) {
                //先执行了stop后执行start就不执行动画了,除非执行了stopAndReset
                if (!mShouldRun!!) {
                    stopAndReset()
                }
                return
            }
            mShouldRun = true
            //            LogUtil.e("动画", this + " 开始");
            mIndex = -1
            if (mRunnable == null) {
                mRunnable = Runnable { show(true) }
            }

            mHandler.post(mRunnable!!)
        }

        /**
         * 从index帧开始播放动画，同步锁防止多线程读帧时，数据安全问题
         */
        @Synchronized
        fun start(index: Int) {
            stopAndReset()
            if (mShouldRun == true) return
            mShouldRun = true
            mIndex = index
            if (mRunnable == null) {
                mRunnable = Runnable { show(true) }
            }

            mHandler.post(mRunnable!!)
        }

        /**
         * 展示图片
         *
         * @param isContinuous 是否自动展示下一张图片
         */
        fun show(isContinuous: Boolean) {
            val imageView = mSoftReferenceImageView.get()
            val imageViewVisibility = isViewContextVisibility(imageView)
            if (!shouldRun() || !imageViewVisibility) {
                mIsRunning = false
                mShouldRun = false
                if (mOnAnimationStoppedListener != null) {
                    mOnAnimationStoppedListener!!.onAnimationStop()
                }
                if (isContinuous || !imageViewVisibility || mShouldRun == false) {
                    return
                }
            }
            if (mShouldRun == false) return
            mIsRunning = true
            if (imageView!!.isShown) {
                unShownCount = 0
//                LogUtil.e("动画", this + " 进行中");
                val imageRes = next
                if (imageRes == -1 || curImageRes == imageRes) return
                this.curImageRes = imageRes
                if (mBitmap != null) {
                    var bitmap: Bitmap? = null
                    try {
                        bitmap = BitmapFactory.decodeResource(
                            imageView.resources,
                            imageRes,
                            mBitmapOptions
                        )
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                    if (bitmap != null) {
                        imageView.setImageBitmap(bitmap)
                    } else {
                        imageView.setImageResource(imageRes)
                        mBitmap!!.recycle()
                        mBitmap = null
                    }
                } else {
                    imageView.setImageResource(imageRes)
                }
            } else {
                if (unShownCount < 100000) {
                    unShownCount++
                }
                if (unShownCount == 20) {
                    //大于20次就认为线程已结束了
                    com.wpf.app.quickutil.utils.LogUtil.e(
                        "动画",
                        "$this 动画应该要结束的，但目前还在后台运行，请查明原因！！！"
                    )
                }
            }
            //新开线程去读下一帧
            if (mRunnable != null && isContinuous && mShouldRun == true) {
                mHandler.postDelayed(mRunnable!!, mDelayMillis.toLong())
            }
        }

        /**
         * 暂停播放
         */
        @Synchronized
        fun stop() {
//            LogUtil.e("动画", this + " 结束");
            mShouldRun = false
            mIsRunning = false
            mRunnable = null
            curImageRes = -1
            unShownCount = 0
        }

        /**
         * 停止播放并重置状态
         */
        @Synchronized
        fun stopAndReset() {
            mShouldRun = null
            mIsRunning = null
            mRunnable = null
            mIndex = -1
            curImageRes = -1
            unShownCount = 0
        }

        /**
         * @return 返回view是否可见
         */
        private fun isViewContextVisibility(view: View?): Boolean {
            if (view == null) return false
            var activity: Activity? = null
            if (view.context is ContextThemeWrapper) {
                if ((view.context as ContextThemeWrapper).baseContext is Activity) {
                    activity = (view.context as ContextThemeWrapper).baseContext as Activity
                }
            }
            if (view.context is Activity) {
                activity = view.context as Activity
            }
            if (activity == null) return false
            return !activity.isDestroyed && !activity.isFinishing
        }

        /**
         * 设置显示在getData里的进度图片
         *
         * @param progress 进度
         */
        fun setAnimProgress(progress: Double) {
            if (mFrames != null) {
                mIndex = (progress * mFrames.size).toInt() - 1
                show(false)
            }
        }

        /**
         * 设置显示在getData里的图片
         *
         * @param index 下标
         */
        fun setAnimIndex(index: Int) {
            if (mFrames != null) {
                mIndex = index
                show(false)
            }
        }

        /**
         * 设置停止播放监听
         */
        fun setOnAnimStopListener(listener: OnAnimationStoppedListener?) {
            this.mOnAnimationStoppedListener = listener
        }

        fun isRunning(): Boolean {
            return mIsRunning ?: false
        }

        fun shouldRun(): Boolean {
            return mShouldRun ?: false
        }
    }

    /**
     * 停止播放监听
     */
    interface OnAnimationStoppedListener {
        fun onAnimationStop()
    }

    companion object {
        // 单例
        private var mInstance: DrawableAnimation? = null

        //获取单例
        fun getInstance(mContext: Context, resId: Int, frameDelay: Int): DrawableAnimation {
            if (mInstance == null) mInstance = DrawableAnimation(mContext)
            mInstance!!.setResId(resId, frameDelay)
            return mInstance!!
        }
    }
}