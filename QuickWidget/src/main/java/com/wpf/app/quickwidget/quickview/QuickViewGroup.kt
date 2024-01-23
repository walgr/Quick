package com.wpf.app.quickwidget.quickview

import android.content.Context
import android.graphics.Canvas
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
import com.wpf.app.quickutil.helper.attribute.AutoGetAttribute
import com.wpf.app.quickutil.helper.attribute.AutoGetAttributeHelper
import com.wpf.app.quickutil.other.GenericEx
import com.wpf.app.quickutil.other.asTo
import com.wpf.app.quickutil.view.matchLayoutParams
import com.wpf.app.quickwidget.R

/**
 * Created by 王朋飞 on 2022/8/31.
 *
 */
//@InternalCoroutinesApi
open class QuickViewGroup<T : ViewGroup> @JvmOverloads constructor(
    context: Context,
    private val attrs: AttributeSet? = null,
    open val defStyleAttr: Int = 0,
    open var addToParent: Boolean = true,
    open val childView: Array<View>? = null
) : ViewGroup(context, attrs, defStyleAttr) {

    protected val attrSet: QuickViewGroupAttrSet

    init {
        attrSet = AutoGetAttributeHelper.init(context, attrs, R.styleable.QuickViewGroup)
        init()
    }

    var shadowView: T? = null
        private set

    @CallSuper
    open fun init() {
        attrSet.addToParent?.let {
            addToParent = it
        }
        initViewGroupByXml()
        initViewGroupByT()
        addChildToT()
        if (!addToParent || isInEditMode) {
            addT()
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
        when (attrSet.groupType) {
            GroupType.TabLayout.type -> {
                this.shadowView = TabLayout(context, attrs, defStyleAttr) as T
            }

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
                addT()
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

class QuickViewGroupAttrSet @JvmOverloads constructor(
    val addToParent: Boolean? = null,
    val groupType: Int = 0,
    val layoutRes: Int = 0,
)

enum class GroupType(val type: Int) {
    LinearLayout(0),
    RelativeLayout(1),
    FrameLayout(2),
    ConstraintLayout(3),
    RadioGroup(4),
    TabLayout(5),
}