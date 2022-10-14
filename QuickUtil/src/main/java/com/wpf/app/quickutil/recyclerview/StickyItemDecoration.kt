package com.wpf.app.quickutil.recyclerview

import android.graphics.Canvas
import android.graphics.Paint
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class StickyItemDecoration(
    private val mStickyView: StickyView
) : RecyclerView.ItemDecoration() {
    /**
     * 吸附的itemView
     */
    private var mStickyItemView: View? = null

    /**
     * 吸附itemView 距离顶部
     */
    private var mStickyItemViewMarginTop = 0F

    /**
     * 吸附itemView 高度
     */
    private var mStickyItemViewHeight = 0F

    /**
     * 滚动过程中当前的UI是否可以找到吸附的view
     */
    private var mCurrentUIFindStickView = false

    /**
     * adapter
     */
    private var mAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>? = null

    /**
     * viewHolder
     */
    private var mViewHolder: RecyclerView.ViewHolder? = null

    /**
     * position list
     */
    private val mStickyPositionList: MutableList<Int> = ArrayList()

    /**
     * layout manager
     */
    private var mLayoutManager: LinearLayoutManager? = null

    /**
     * 绑定数据的position
     */
    private var mBindDataPosition = -1

    /**
     * paint
     */
    private var mPaint: Paint? = null

    init {
        initPaint()
    }

    /**
     * init paint
     */
    private fun initPaint() {
        mPaint = Paint()
        mPaint?.isAntiAlias = true
    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)
        if (parent.adapter!!.itemCount <= 0) return
        mLayoutManager = parent.layoutManager as? LinearLayoutManager
        mCurrentUIFindStickView = false
        clearStickyPositionList()
        var m = 0
        val size = parent.childCount
        while (m < size) {
            val view: View = parent.getChildAt(m)
            /**
             * 如果是吸附的view
             */
            if (mStickyView.isStickyView(parent, view)) {
                mCurrentUIFindStickView = true
                getStickyViewHolder(parent, view)
                cacheStickyViewPosition(m)
                if (view.top <= 0) {
                    bindDataForStickyView(mLayoutManager!!.findFirstVisibleItemPosition(), parent.measuredWidth)
                } else {
                    if (mStickyPositionList.size > 0) {
                        if (mStickyPositionList.size == 1) {
                            bindDataForStickyView(mStickyPositionList[0], parent.measuredWidth)
                        } else {
                            val currentPosition = getStickyViewPositionOfRecyclerView(m)
                            val indexOfCurrentPosition = mStickyPositionList.lastIndexOf(currentPosition)
                            if (indexOfCurrentPosition >= 1) bindDataForStickyView(
                                mStickyPositionList[indexOfCurrentPosition - 1],
                                parent.measuredWidth
                            )
                        }
                    }
                }
                if (view.top > 0 && view.top <= mStickyItemViewHeight) {
                    mStickyItemViewMarginTop = mStickyItemViewHeight - view.top
                } else {
                    mStickyItemViewMarginTop = 0F
                    val nextStickyView: View? = getNextStickyView(parent)
                    if (nextStickyView != null && nextStickyView.top <= mStickyItemViewHeight) {
                        mStickyItemViewMarginTop = mStickyItemViewHeight - nextStickyView.top
                    }
                }
                drawStickyItemView(c)
                break
            }
            m++
        }
        if (!mCurrentUIFindStickView) {
            mStickyItemViewMarginTop = 0F
            if (mLayoutManager!!.findFirstVisibleItemPosition() + parent.childCount == parent.adapter!!.itemCount && mStickyPositionList.size > 0) {
                bindDataForStickyView(mStickyPositionList[mStickyPositionList.size - 1], parent.measuredWidth)
            }
            drawStickyItemView(c)
        }
    }

    /**
     * 清空吸附position集合
     */
    private fun clearStickyPositionList() {
        if (mLayoutManager!!.findFirstVisibleItemPosition() == 0) {
            mStickyPositionList.clear()
        }
    }

    /**
     * 得到下一个吸附View
     * @param parent
     * @return
     */
    private fun getNextStickyView(parent: RecyclerView): View? {
        var num = 0
        var nextStickyView: View? = null
        var m = 0
        val size = parent.childCount
        while (m < size) {
            val view: View = parent.getChildAt(m)
            if (mStickyView.isStickyView(parent, view)) {
                nextStickyView = view
                num++
            }
            if (num == 2) break
            m++
        }
        return if (num >= 2) nextStickyView else null
    }

    /**
     * 给StickyView绑定数据
     * @param position
     */
    private fun bindDataForStickyView(position: Int, width: Int) {
        if (mBindDataPosition == position || mViewHolder == null) return
        mBindDataPosition = position
        mAdapter?.onBindViewHolder(mViewHolder!!, mBindDataPosition)
        measureLayoutStickyItemView(width)
        mStickyItemViewHeight = (mViewHolder!!.itemView.bottom - mViewHolder!!.itemView.top).toFloat()
    }

    /**
     * 缓存吸附的view position
     * @param m
     */
    private fun cacheStickyViewPosition(m: Int) {
        val position = getStickyViewPositionOfRecyclerView(m)
        if (!mStickyPositionList.contains(position)) {
            mStickyPositionList.add(position)
        }
    }

    /**
     * 得到吸附view在RecyclerView中 的position
     * @param m
     * @return
     */
    private fun getStickyViewPositionOfRecyclerView(m: Int): Int {
        return mLayoutManager!!.findFirstVisibleItemPosition() + m
    }

    /**
     * 得到吸附viewHolder
     * @param recyclerView
     */
    private fun getStickyViewHolder(recyclerView: RecyclerView, view: View) {
        if (mAdapter != null) return
        mAdapter = recyclerView.adapter
        mViewHolder = mAdapter?.onCreateViewHolder(recyclerView, mStickyView.getStickViewType(recyclerView, view))
        mStickyItemView = mViewHolder?.itemView
    }

    /**
     * 计算布局吸附的itemView
     * @param parentWidth
     */
    private fun measureLayoutStickyItemView(parentWidth: Int) {
        if (mStickyItemView == null || !mStickyItemView!!.isLayoutRequested) return
        val widthSpec: Int = View.MeasureSpec.makeMeasureSpec(parentWidth, View.MeasureSpec.EXACTLY)
        val heightSpec: Int
        val layoutParams: ViewGroup.LayoutParams? = mStickyItemView?.layoutParams
        heightSpec = if (layoutParams != null && layoutParams.height > 0) {
            View.MeasureSpec.makeMeasureSpec(layoutParams.height, View.MeasureSpec.EXACTLY)
        } else {
            View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        }
        mStickyItemView?.measure(widthSpec, heightSpec)
        mStickyItemView?.layout(0, 0, mStickyItemView?.measuredWidth ?: 0, mStickyItemView?.measuredHeight ?: 0)
    }

    /**
     * 绘制吸附的itemView
     * @param canvas
     */
    private fun drawStickyItemView(canvas: Canvas) {
        if (mStickyItemView == null) return
        val saveCount: Int = canvas.save()
        canvas.translate(0f, -mStickyItemViewMarginTop)
        mStickyItemView?.draw(canvas)
        canvas.restoreToCount(saveCount)
    }
}