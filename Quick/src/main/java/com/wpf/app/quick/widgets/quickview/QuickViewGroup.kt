package com.wpf.app.quick.widgets.quickview

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.util.LayoutDirection
import android.view.View
import android.view.View.LAYOUT_DIRECTION_LOCALE
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.view.children
import com.wpf.app.quick.utils.GenericEx
import com.wpf.app.quick.utils.LogUtil
import com.wpf.app.quick.widgets.quickview.util.QuickMeasure

/**
 * Created by 王朋飞 on 2022/8/31.
 *
 */
open class QuickViewGroup<T : ViewGroup> @JvmOverloads constructor(
    val mContext: Context,
    val attributeSet: AttributeSet? = null,
    val defStyleAttr: Int = 0,
) : ViewGroup(mContext, attributeSet, defStyleAttr) {

    init {
        initViewGroupByT()
    }

    private var shadowView: T? = null

    private fun initViewGroupByT() {
        val tCls: Class<T>? = GenericEx.get0Clazz(this)
        tCls?.let {
            val t =
                tCls.getConstructor(Context::class.java, AttributeSet::class.java, Int::class.java)
            this.shadowView = t.newInstance(mContext, attributeSet, defStyleAttr)
            if (!isInEditMode) {
                post {
                    addChildToT()
                    addTToThis()
                }
            }
        }
    }

    private fun addChildToT() {
        val childList = mutableListOf<View>()
        childList.addAll(children.toMutableList())
        removeAllViews()
//        shadowView?.removeAllViews()
        childList.forEach {
            this.shadowView?.addView(it)
        }
    }

    private fun addTToParent() {
        val parentGroup = parent as? ViewGroup ?: return
        val position = parentGroup.indexOfChild(this)
//        parentGroup.removeView(this)
        parentGroup.addView(shadowView, position)
        visibility = View.INVISIBLE
    }

    private fun addTToThis() {
        if (shadowView?.parent == null) {
            LogUtil.e("shadowView:${shadowView} 数量:${shadowView?.childCount}")
            this.shadowView?.layoutParams = layoutParams
            this.addView(shadowView)
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        shadowView?.let {
            if (isInEditMode) {
                addChildToT()
                addTToParent()
            } else {
//                super.onMeasure(widthMeasureSpec, heightMeasureSpec)
//                return
            }
            if (it is QuickMeasure) {
                it.Measure(widthMeasureSpec, heightMeasureSpec)
            } else {
                it.measure(widthMeasureSpec, heightMeasureSpec)
            }
            val viewMeasureWidth = it.measuredWidth
            val viewMeasureHeight = it.measuredHeight
            val specModeWidth = MeasureSpec.getMode(widthMeasureSpec)
            val specModeHeight = MeasureSpec.getMode(heightMeasureSpec)
            LogUtil.e("viewMeasureWidth:${viewMeasureWidth}")
            LogUtil.e("viewMeasureHeight:${viewMeasureHeight}")
            super.onMeasure(
                MeasureSpec.makeMeasureSpec(viewMeasureWidth, specModeWidth),
                MeasureSpec.makeMeasureSpec(viewMeasureHeight, specModeHeight)
            )
            return@let
        } ?: let {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        }
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        if (!isInEditMode) {
            this.shadowView?.layout(l, t, r, b)
        }
//        this.shadowView?.let {
//            LogUtil.e("onLayout " + this.shadowView?.toString())
//            (it as? QuickMeasure)?.Layout(changed, l, t, r, b)
//        }
    }

    override fun onDraw(canvas: Canvas?) {
        LogUtil.e("onDraw " + this.shadowView?.toString())
        (this.shadowView as? QuickMeasure)?.Draw(canvas)
    }
}