package com.wpf.app.quickwidget.base

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
import com.wpf.app.quickutil.helper.generic.asTo
import com.wpf.app.quickutil.helper.generic.nullDefault
import com.wpf.app.quickutil.helper.parent
import com.wpf.app.quickwidget.R

/**
 * Created by 王朋飞 on 2022/8/31.
 *
 */
@Suppress("LeakingThis")
open class QuickViewGroupNoAttrs<T : ViewGroup> @JvmOverloads constructor(
    context: Context,
    private val attrs: AttributeSet? = null,
    private val defStyleAttr: Int = 0,
    private var addToParent: Boolean = true,
    private val forceGenerics: Boolean = false,             //强制泛型初始化
    private val forceGenericsCls: Class<T>? = null,         //强制Cls初始化
    private var addChildToParent: Boolean = false,           //是否把子View添加到父View
) : ViewGroup(context, attrs, defStyleAttr), QuickViewGroupI<T> {

    private val attrSet: QuickViewGroupAttrSet =
        AutoGetAttributeHelper.init(context, attrs, R.styleable.QuickViewGroup)

    init {
        init()
    }

    var shadowView: ViewGroup? = null
        private set

    @CallSuper
    open fun init() {
        attrSet.addToParent?.let {
            addToParent = it
        }
        if (isInEditMode) {
            addToParent = false
        }
        if (!addChildToParent) {
            shadowView = if (attrSet.groupType != -1) {
                initViewGroupByXml(shadowView, attrSet.groupType, context, attrs, defStyleAttr)
            } else if (forceGenerics || forceGenericsCls != null) {
                initViewGroupByT(shadowView, context, attrs, defStyleAttr, forceGenericsCls)
            } else {
                initViewGroupByT(shadowView, context, attrs, defStyleAttr)
            }
            addChildToGroup(shadowView, this)
            if (!addToParent) {
                addT(false, shadowView, this)
            }
        } else {
            addChildToGroup(parent(), this)
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        shadowView?.let {
            if (addToParent && !isInEditMode) {
                addT(addToParent, shadowView, this)
            }
            it.measure(widthMeasureSpec, heightMeasureSpec)
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
            return shadowView.asTo<TabLayout>()?.tabCount.nullDefault(0)
        }
        return shadowView?.childCount.nullDefault(0)
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

    override fun setPadding(left: Int, top: Int, right: Int, bottom: Int) {
        super.setPadding(left, top, right, bottom)
        shadowView?.setPadding(left, top, right, bottom)
    }

    override fun generateDefaultLayoutParams(): LayoutParams {
        return generateLayoutParams(attrs)
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
                generateOtherLayoutParams(attrs)
            }
        }
    }

    open fun generateOtherLayoutParams(attrs: AttributeSet?): LayoutParams {
        return MarginLayoutParams(context, attrs)
    }

    protected class QuickViewGroupAttrSet @JvmOverloads constructor(
        val addToParent: Boolean? = null,
        val groupType: Int = -1,
    )
}