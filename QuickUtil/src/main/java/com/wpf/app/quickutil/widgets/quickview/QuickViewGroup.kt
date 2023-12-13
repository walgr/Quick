package com.wpf.app.quickutil.widgets.quickview

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.RadioGroup
import android.widget.RelativeLayout
import androidx.constraintlayout.widget.ConstraintLayout
import com.wpf.app.quickutil.widgets.quickview.helper.GroupType
import com.wpf.app.quickutil.widgets.quickview.helper.QuickViewGroupAttrSetHelper
import com.wpf.app.quickutil.other.GenericEx
import com.wpf.app.quickutil.other.matchLayoutParams
import com.wpf.app.quickutil.widgets.quickview.util.QuickMeasure
import com.wpf.app.quickutil.other.removeParent

/**
 * Created by 王朋飞 on 2022/8/31.
 *
 */
open class QuickViewGroup<T : ViewGroup> @JvmOverloads constructor(
    mContext: Context,
    val attributeSet: AttributeSet? = null,
    val defStyleAttr: Int = 0,
    open val addToParent: Boolean = true,
    open val childView: Array<View>? = null
) : ViewGroup(mContext, attributeSet, defStyleAttr) {

    protected var attrSet: QuickViewGroupAttrSetHelper? = null

    init {
        init()
    }

    private var shadowView: T? = null

    open fun init() {
        if (attrSet == null) {
            attributeSet?.let {
                attrSet = QuickViewGroupAttrSetHelper(context, it)
            }
        }
        initViewGroupByXml()
        initViewGroupByT()
    }

    private fun initViewGroupByT() {
        if (this.shadowView != null) return
        val tCls: Class<T>? = GenericEx.get0Clazz(this)
        if ("ViewGroup" == tCls?.simpleName) return
        tCls?.let {
            val t =
                tCls.getConstructor(Context::class.java, AttributeSet::class.java, Int::class.java)
            this.shadowView = t.newInstance(context, attributeSet, defStyleAttr)
            this.shadowView?.layoutParams = matchLayoutParams
        }
    }

    private fun initViewGroupByXml() {
        if (this.shadowView != null) return
        attrSet?.let {
            when (it.groupType) {
                GroupType.LinearLayout.type -> {
                    this.shadowView = LinearLayout(context, attributeSet, defStyleAttr) as T
                }

                GroupType.RelativeLayout.type -> {
                    this.shadowView = RelativeLayout(context, attributeSet, defStyleAttr) as T
                }

                GroupType.FrameLayout.type -> {
                    this.shadowView = FrameLayout(context, attributeSet, defStyleAttr) as T
                }

                GroupType.ConstraintLayout.type -> {
                    this.shadowView = ConstraintLayout(context, attributeSet, defStyleAttr) as T
                }

                GroupType.RadioGroup.type -> {
                    this.shadowView = RadioGroup(context, attributeSet) as T
                }

                else -> {
                    this.shadowView = LinearLayout(context, attributeSet, defStyleAttr) as T
                }
            }
        }
    }

    private fun addChildToT() {
        childView?.forEach {
            it.removeParent()
            this.shadowView?.addView(it)
        }
        if (shadowView?.childCount != 0) return
        if (childCount == 0) return
        val childList = mutableListOf<View>()
        childList.addAll(children())
        removeAllViews()
        shadowView?.removeAllViews()
        childList.forEach {
            this.shadowView?.addView(it)
        }
    }

    private fun children(): List<View> {
        val result = arrayListOf<View>()
        for (i in 0 until childCount) {
            result.add(getChildAt(i))
        }
        return result
    }

    private fun addTToParent() {
        if (shadowView?.parent != null) return
        val parentGroup = parent as? ViewGroup ?: return
        val position = parentGroup.indexOfChild(this)
        parentGroup.removeView(this)
        parentGroup.addView(shadowView, position)
    }

    private fun addTToThis() {
        if (shadowView?.childCount == 0) return
        if (shadowView?.parent != null) return
        shadowView?.let {
            it.layoutParams = layoutParams
            it.removeParent()
            this.addView(shadowView, 0)
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        shadowView?.let {
            addChildToT()
            if (isInEditMode) {
                addTToThis()
            } else {
                if (addToParent) {
                    addTToParent()
                } else {
                    addTToThis()
                }
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
            setMeasuredDimension(
                MeasureSpec.makeMeasureSpec(viewMeasureWidth, specModeWidth),
                MeasureSpec.makeMeasureSpec(viewMeasureHeight, specModeHeight)
            )
        } ?: let {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        }
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        this.shadowView?.let { shadowView ->
            (shadowView as? QuickMeasure)?.Layout(
                changed,
                0,
                0,
                shadowView.measuredWidth,
                shadowView.measuredHeight
            ) ?: let {
                shadowView.layout(0, 0, shadowView.measuredWidth, shadowView.measuredHeight)
            }
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        (this.shadowView as? QuickMeasure)?.Draw(canvas)
    }

    override fun generateLayoutParams(attrs: AttributeSet?): LayoutParams {
        return when (shadowView) {
            is RadioGroup -> {
                RadioGroup.LayoutParams(context, attrs)
            }

            is LinearLayout -> {
                LinearLayout.LayoutParams(context, attrs)
            }

            is RelativeLayout -> {
                RelativeLayout.LayoutParams(context, attrs)
            }

            is FrameLayout -> {
                FrameLayout.LayoutParams(context, attrs)
            }

            is ConstraintLayout -> {
                ConstraintLayout.LayoutParams(context, attrs)
            }

            else -> {
                MarginLayoutParams(context, attrs)
            }
        }
    }

    companion object {
        inline fun <reified T : ViewGroup> create(
            mContext: Context,
            attributeSet: AttributeSet? = null,
            defStyleAttr: Int = 0,
            addToParent: Boolean = true,
            childView: Array<View>? = null
        ) = object : QuickViewGroup<T>(
            mContext,
            attributeSet,
            defStyleAttr,
            addToParent = addToParent,
            childView = childView
        ) {}
    }

    fun build() {
        post {
            addChildToT()
            if (addToParent) {
                addTToParent()
            } else {
                addTToThis()
            }
        }
    }
}