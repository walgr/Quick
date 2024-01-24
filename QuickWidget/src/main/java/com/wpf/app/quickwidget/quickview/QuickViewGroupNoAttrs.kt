package com.wpf.app.quickwidget.quickview

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.RadioGroup
import android.widget.RelativeLayout
import androidx.annotation.CallSuper
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.tabs.TabLayout
import com.wpf.app.quickutil.helper.attribute.AutoGetAttributeHelper
import com.wpf.app.quickutil.other.asTo
import com.wpf.app.quickwidget.R

/**
 * Created by 王朋飞 on 2022/8/31.
 *
 */
//@InternalCoroutinesApi
open class QuickViewGroupNoAttrs<T : ViewGroup> @JvmOverloads constructor(
    context: Context,
    private val attrs: AttributeSet? = null,
    open val defStyleAttr: Int = 0,
    open var addToParent: Boolean = true,
    open val childView: Array<View>? = null
) : ViewGroup(context, attrs, defStyleAttr), QuickViewGroupI<T> {

    protected val attrSet: QuickViewGroupAttrSet

    init {
        attrSet = AutoGetAttributeHelper.init(context, attrs, R.styleable.QuickViewGroup)
        this.init()
    }

    var shadowView: T? = null
        private set

    @CallSuper
    open fun init() {
        attrSet.addToParent?.let {
            addToParent = it
        }
        if (isInEditMode) {
            addToParent = false
        }
        shadowView = initViewGroupByXml(shadowView, attrSet.groupType, context, attrs, defStyleAttr)
            ?: initViewGroupByT(shadowView, context, attrs, defStyleAttr)
        addChildToT(shadowView, this)
        if (!addToParent) {
            addT(false, shadowView, this)
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
                addT(addToParent, shadowView, this)
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
        if (shadowView is TabLayout) {
            return shadowView.asTo<TabLayout>()?.getTabAt(index)?.view
        }
        return shadowView?.getChildAt(index)
    }

    fun indexOfChildInShadow(child: View?): Int {
        if (shadowView is TabLayout) {
            val tabLayout = shadowView.asTo<TabLayout>() ?: return -1
            repeat(tabLayout.tabCount) {
                if (child == tabLayout.getTabAt(it)?.view)
                    return it
            }
            return -1
        }
        return shadowView?.indexOfChild(child) ?: -1
    }

    override fun addView(child: View?, index: Int, params: LayoutParams?) {
        val childIsShadow = child == shadowView
        if (!childIsShadow) {
            if (shadowView is TabLayout) {
                shadowView.asTo<TabLayout>()?.apply {
                    addTab(newTab().setCustomView(child))
                }
            } else {
                shadowView?.addView(child, index, params)
            }
        }
        if (!addToParent || isInEditMode) {
            if (childIsShadow) {
                super.addView(child, index, params)
            }
        }
    }

    override fun removeView(view: View?) {
        if (shadowView is TabLayout) {
            shadowView.asTo<TabLayout>()?.apply {
                repeat(tabCount) {
                    val tab = getTabAt(it)
                    tab?.let {
                        if (view == tab.view)
                            removeTab(tab)
                    }
                }
            }
            return
        }
        shadowView?.removeView(view) ?: super.removeView(view)
    }

    fun getRealChildCount(): Int {
        if (shadowView is TabLayout) {
            return shadowView.asTo<TabLayout>()?.tabCount ?: 0
        }
        return shadowView?.childCount ?: 0
    }

    override fun setClipChildren(clipChildren: Boolean) {
        super.setClipChildren(clipChildren)
        shadowView?.clipChildren = clipChildren
    }

    override fun setAlpha(alpha: Float) {
        super.setAlpha(alpha)
        shadowView?.alpha = alpha
    }

    override fun setActivated(activated: Boolean) {
        super.setActivated(activated)
        shadowView?.isActivated = activated
    }

    override fun setAnimation(animation: Animation?) {
        super.setAnimation(animation)
        shadowView?.animation = animation
    }

    override fun setBackground(background: Drawable?) {
        super.setBackground(background)
        shadowView?.background = background
    }

    override fun setBackgroundColor(color: Int) {
        super.setBackgroundColor(color)
        shadowView?.setBackgroundColor(color)
    }

    override fun setBackgroundResource(resid: Int) {
        super.setBackgroundResource(resid)
        shadowView?.setBackgroundResource(resid)
    }

    override fun generateDefaultLayoutParams(): LayoutParams {
        return generateLayoutParams(attrs)
    }

    override fun setPadding(left: Int, top: Int, right: Int, bottom: Int) {
        super.setPadding(left, top, right, bottom)
        shadowView?.setPadding(left, top, right, bottom)
    }

    override fun generateLayoutParams(attrs: AttributeSet?): LayoutParams {
        return when (shadowView) {
            is TabLayout -> {
                LinearLayout.LayoutParams(context, attrs)
            }

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

    protected class QuickViewGroupAttrSet @JvmOverloads constructor(
        val addToParent: Boolean? = null,
        val groupType: Int = 0,
        val layoutRes: Int = 0,
    )
}