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
import androidx.annotation.CallSuper
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.children
import com.wpf.app.quickutil.LogUtil
import com.wpf.app.quickutil.widgets.quickview.helper.GroupType
import com.wpf.app.quickutil.widgets.quickview.helper.QuickViewGroupAttrSetHelper
import com.wpf.app.quickutil.other.GenericEx
import com.wpf.app.quickutil.other.matchLayoutParams
import com.wpf.app.quickutil.widgets.quickview.util.QuickMeasure
import com.wpf.app.quickutil.other.removeParent
import kotlinx.coroutines.InternalCoroutinesApi

/**
 * Created by 王朋飞 on 2022/8/31.
 *
 */
//@InternalCoroutinesApi
open class QuickViewGroup<T : ViewGroup> @JvmOverloads constructor(
    mContext: Context,
    val attrs: AttributeSet? = null,
    val defStyleAttr: Int = 0,
    open val addToParent: Boolean = true,
    open val childView: Array<View>? = null
) : ViewGroup(mContext, attrs, defStyleAttr) {

    protected var attrSet: QuickViewGroupAttrSetHelper? = null

    init {
        init()
    }

    private var shadowView: T? = null

    @CallSuper
    open fun init() {
        if (attrSet == null) {
            attrs?.let {
                attrSet = QuickViewGroupAttrSetHelper(context, it)
            }
        }
        initViewGroupByXml()
        initViewGroupByT()
        addChildToT()
        if (!addToParent || isInEditMode) {
            addTToThis()
        }
    }

    private fun initViewGroupByT() {
        if (this.shadowView != null) return
        val tCls: Class<T>? = GenericEx.get0Clazz(this)
        if ("ViewGroup" == tCls?.simpleName) return
        tCls?.let {
            val t =
                tCls.getConstructor(Context::class.java, AttributeSet::class.java, Int::class.java)
            this.shadowView = t.newInstance(context, attrs, defStyleAttr)
            this.shadowView?.layoutParams = matchLayoutParams
        }
    }

    private fun initViewGroupByXml() {
        if (this.shadowView != null) return
        attrSet?.let {
            when (it.groupType) {
                GroupType.LinearLayout.type -> {
                    this.shadowView = LinearLayout(context, attrs, defStyleAttr) as T
                }

                GroupType.RelativeLayout.type -> {
                    this.shadowView = RelativeLayout(context, attrs, defStyleAttr) as T
                }

                GroupType.FrameLayout.type -> {
                    this.shadowView = FrameLayout(context, attrs, defStyleAttr) as T
                }

                GroupType.ConstraintLayout.type -> {
                    this.shadowView = ConstraintLayout(context, attrs, defStyleAttr) as T
                }

                GroupType.RadioGroup.type -> {
                    this.shadowView = RadioGroup(context, attrs) as T
                }

                else -> {
                    this.shadowView = LinearLayout(context, attrs, defStyleAttr) as T
                }
            }
        }
    }

    private fun addChildToT() {
        childView?.forEach {
            this.shadowView?.addView(it)
        }
    }

    private fun addTToParent() {
        if (shadowView?.parent != null) return
        val parentGroup = parent as? ViewGroup ?: return
        val position = parentGroup.indexOfChild(this)
        parentGroup.removeView(this)
        layoutParams?.let {
            shadowView?.layoutParams = layoutParams
        }
        parentGroup.addView(shadowView, position)
    }

    private fun addTToThis() {
        if (shadowView?.parent != null) return
        shadowView?.let {
            super.addView(shadowView)
        }
    }

    open fun addT() {
        if (addToParent) {
            addTToParent()
        } else {
            addTToThis()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        shadowView?.let {
            it.measure(widthMeasureSpec, heightMeasureSpec)
            val viewMeasureWidth = it.measuredWidth
            val viewMeasureHeight = it.measuredHeight
            val specModeWidth = MeasureSpec.getMode(widthMeasureSpec)
            val specModeHeight = MeasureSpec.getMode(heightMeasureSpec)
            setMeasuredDimension(
                MeasureSpec.makeMeasureSpec(viewMeasureWidth, specModeWidth),
                MeasureSpec.makeMeasureSpec(viewMeasureHeight, specModeHeight)
            )
            if (addToParent && !isInEditMode) {
                addTToParent()
            }
        } ?: let {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        }
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        shadowView?.let { shadowView ->
            shadowView.layout(0, 0, shadowView.measuredWidth, shadowView.measuredHeight)
        }
    }

    fun getChildAtInShadow(index: Int): View? {
        return shadowView?.getChildAt(index)
    }

    fun indexOfChildInShadow(child: View?): Int {
        return shadowView?.indexOfChild(child) ?: 0
    }

    override fun addView(child: View?, index: Int, params: LayoutParams?) {
        val childIsShadow = child == shadowView
        if (!childIsShadow) {
            shadowView?.addView(child, index, params)
        }
        if (!addToParent || isInEditMode) {
            if (childIsShadow) {
                super.addView(child, index, params)
            }
        }
    }

    override fun removeView(view: View?) {
        shadowView?.removeView(view) ?: super.removeView(view)
    }

    fun getRealChildCount(): Int = shadowView?.childCount ?: 0

    override fun generateDefaultLayoutParams(): LayoutParams {
        return generateLayoutParams(attrs)
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
            addT()
        }
    }
}