package com.wpf.app.quickwidget.title

import android.content.Context
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.annotation.IntDef
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.core.view.updateLayoutParams
import com.wpf.app.quickutil.helper.attribute.AutoGetAttributeHelper
import com.wpf.app.quickutil.helper.children
import com.wpf.app.quickutil.helper.copy
import com.wpf.app.quickutil.helper.dp
import com.wpf.app.quickutil.helper.removeParent
import com.wpf.app.quickutil.helper.sp
import com.wpf.app.quickutil.helper.toColor
import com.wpf.app.quickutil.helper.toDrawable
import com.wpf.app.quickutil.helper.toView
import com.wpf.app.quickutil.other.nullDefault
import com.wpf.app.quickutil.widget.wishLayoutParams
import com.wpf.app.quickwidget.R
import com.wpf.app.quickwidget.group.QuickSpaceLinearLayout
import kotlin.math.max

@Suppress("LeakingThis")
open class QuickTitleView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : LinearLayout(context, attrs, defStyleAttr) {

    var contentLayout: ViewGroup? = null
    var backLayout: ViewGroup? = null
    var ivBack: ImageView? = null
    var titleGroup: ViewGroup? = null
    var tvTitle: TextView? = null
    var tvSubTitle: TextView? = null
    var backGroup: QuickSpaceLinearLayout? = null
    var line: View? = null
    private var moreGroup: QuickSpaceLinearLayout? = null

    var clickListener: CommonClickListener? = null

    init {
        orientation = VERTICAL
        R.layout.toolbar_layout.toView(context, this, true)
        minimumHeight = 44.dp
        contentLayout = findViewById(R.id.titleContentLayout)
        titleGroup = findViewById(R.id.titleGroup)
        backLayout = findViewById(R.id.backLayout)
        backGroup = findViewById(R.id.backGroup)
        ivBack = findViewById(R.id.ivBack)
        tvTitle = findViewById(R.id.tvTitle)
        tvSubTitle = findViewById(R.id.tvSubTitle)
        moreGroup = findViewById(R.id.moreGroup)
        line = findViewById(R.id.line)

        if (commonClickListener != null || clickListener != null) {
            ivBack?.setOnClickListener {
                clickListener?.onBackClick(it)
                    ?: commonClickListener?.onBackClick(it)
            }

            tvTitle?.setOnClickListener {
                clickListener?.onTitleClick(it)
                    ?: commonClickListener?.onTitleClick(it)
            }

            tvSubTitle?.setOnClickListener {
                clickListener?.onSubTitleClick(it)
                    ?: commonClickListener?.onSubTitleClick(it)
            }
        }
        initAttrsInXml(attrs)
    }

    private var curAttrs: QuickTitleAttrs? = null
    open fun initAttrsInXml(attrs: AttributeSet?) {
        curAttrs = AutoGetAttributeHelper.init(
            context,
            attrs,
            R.styleable.QuickTitleView,
            QuickTitleAttrs()
        )
        setAttrsToView(curAttrs!!)
    }

    open fun setAttrsToView(attrs: QuickTitleAttrs) {
        attrs.apply {
            initDataByXml(context)
            if (layoutParams == null || layoutParams?.height == ViewGroup.LayoutParams.WRAP_CONTENT || layoutParams?.height == ViewGroup.LayoutParams.MATCH_PARENT) {
                height?.let {
                    post {
                        updateLayoutParams<ViewGroup.LayoutParams> {
                            wishLayoutParams<ViewGroup.LayoutParams>().height = it
                        }
                    }
                }
            }
            if (this@QuickTitleView.background == null) {
                background?.let {
                    this@QuickTitleView.background = it
                }
            }
            showBackIcon?.let {
                showBack(it)
            }
            backIcon?.let {
                setBackIcon(it)
            }

            titleStr?.let {
                setTitle(it)
            }
            titleColor?.let {
                setTitleColor(it)
            }
            titleSize?.let {
                setTitleSize(it)
            }
            titleBold?.let {
                setTitleIsBold(it)
            }

            subTitleStr?.let {
                setSubTitle(it)
            }
            subTitleColor?.let {
                setSubTitleColor(it)
            }
            subTitleSize?.let {
                setSubTitleSize(it)
            }
            subTitleBold?.let {
                setSubTitleIsBold(it)
            }
            titleSpace?.let {
                setSubTitleMarginTop(it)
            }

            showLine?.let {
                showLine(it)
            }
            contentGravity?.let {
                setContentGravity(
                    it,
                    showBackIcon = showBackIcon ?: false,
                    isLinearLayout = isLinearLayout ?: true,
                    isAbsoluteCenter = isAbsoluteCenter ?: true,
                )
            }

            backGroupChild?.let {
                backGroup?.let { backGroup ->
                    val childList = it.invoke(backGroup)
                    childList.forEach {
                        if (it.parent == null) {
                            backGroup.addView(it)
                        }
                    }
                }
            }

            moreGroupChild?.let {
                moreGroup?.let { moreGroup ->
                    val childList = it.invoke(moreGroup)
                    childList.forEach {
                        if (it.parent == null) {
                            moreGroup.addView(it)
                        }
                    }
                }
            }
        }
    }

    fun showBack(show: Boolean) {
        ivBack?.isVisible = show
        backLayout?.isVisible = show
    }

    fun setBackIcon(@DrawableRes iconRes: Int) {
        ivBack?.setImageResource(iconRes)
    }

    open fun setContentGravity(
        @ContentGravity gravity: Int,
        showBackIcon: Boolean = true,
        isLinearLayout: Boolean = true,
        isAbsoluteCenter: Boolean = true,
    ) {
        titleGroup?.updateLayoutParams<ConstraintLayout.LayoutParams> {
            if (isLinearLayout) {
                this.startToEnd = R.id.backGroup
                this.endToStart = R.id.moreGroup
            } else {
                this.startToStart = ConstraintLayout.LayoutParams.PARENT_ID
                this.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
            }
            if (gravity == CONTENT_GRAVITY_CENTER) {
                horizontalBias = 0.5f
                if (isLinearLayout && isAbsoluteCenter) {
                    ivBack?.post {
                        backGroup?.post {
                            moreGroup?.post {
                                val leftViewW =
                                    ivBack?.width.nullDefault(0) + backGroup?.width.nullDefault(0)
                                val rightViewW = moreGroup?.width.nullDefault(0)
                                val maxW = max(leftViewW, rightViewW)
                                val isDealLeft = maxW == rightViewW
                                titleGroup?.setPadding(
                                    paddingLeft + if (isDealLeft) max(0, maxW - leftViewW) else 0,
                                    paddingTop,
                                    paddingRight + if (!isDealLeft) max(
                                        0,
                                        maxW - rightViewW
                                    ) else 0,
                                    paddingBottom
                                )
                            }
                        }
                    }
                }
            }
            if (gravity == CONTENT_GRAVITY_START) {
                horizontalBias = 0f
            }
            if (gravity == CONTENT_GRAVITY_END) {
                horizontalBias = 1f
            }
        }
    }

    fun setTitle(title: CharSequence?) {
        tvTitle?.text = title
    }

    fun setTitleColor(@ColorInt color: Int) {
        tvTitle?.setTextColor(color)
    }

    fun setTitleSize(size: Float) {
        tvTitle?.setTextSize(TypedValue.COMPLEX_UNIT_PX, size)
    }

    fun setTitleIsBold(bold: Boolean = true) {
        tvTitle?.setTypeface(
            if (bold) Typeface.defaultFromStyle(Typeface.BOLD) else Typeface.defaultFromStyle(
                Typeface.NORMAL
            )
        )
    }

    fun showSubTitle(show: Boolean) {
        tvSubTitle?.isVisible = show
    }

    fun setSubTitle(title: CharSequence?) {
        tvSubTitle?.text = title
        showSubTitle(title?.isNotEmpty() == true)
    }

    fun setSubTitleColor(@ColorInt color: Int) {
        tvSubTitle?.setTextColor(color)
    }

    fun setSubTitleSize(size: Float) {
        tvSubTitle?.setTextSize(TypedValue.COMPLEX_UNIT_PX, size)
    }

    fun setSubTitleIsBold(bold: Boolean = true) {
        tvSubTitle?.setTypeface(
            if (bold) Typeface.defaultFromStyle(Typeface.BOLD) else Typeface.defaultFromStyle(
                Typeface.NORMAL
            )
        )
    }

    fun setSubTitleMarginTop(marginTop: Int) {
        tvSubTitle?.updateLayoutParams<MarginLayoutParams> {
            topMargin = marginTop
        }
    }

    fun showLine(show: Boolean) {
        line?.isVisible = show
    }

    fun getMoreGroup() = moreGroup

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)
        curAttrs?.apply {
            if (isInEditMode && isAbsoluteCenter == true && titleGroup != null && ivBack != null && moreGroup != null) {
                val minL =
                    if (showBackIcon == true && isLinearLayout == true) ivBack!!.width else 0
                val minE = if (isLinearLayout == true) moreGroup!!.width else 0
                val titleGroupL = when (contentGravity) {
                    CONTENT_GRAVITY_CENTER -> {
                        ((l + r - titleGroup!!.width) / 2).coerceIn(l + minL, (l + r) / 2)
                    }

                    CONTENT_GRAVITY_START -> {
                        minL
                    }

                    else -> {
                        (r - minE - titleGroup!!.width).coerceIn(
                            l + minL, r - minE
                        )
                    }
                }
                val titleGroupE = (titleGroupL + titleGroup!!.width).coerceIn(titleGroupL, r - minE)
                val titleGroupT = (t + b - titleGroup!!.height) / 2
                titleGroup?.layout(
                    titleGroupL, titleGroupT, titleGroupE, titleGroupT + titleGroup!!.height
                )
            }
        }
    }

    override fun addView(child: View?, index: Int, params: ViewGroup.LayoutParams?) {
        if (child == null || child.id == R.id.titleContentLayout || child.id == R.id.line) {
            super.addView(child, index, params)
        } else {
            when (child) {
                is BackGroup -> {
                    if (child.attrs.removeAllDefaultChild) {
                        backGroup?.removeAllViews()
                    }
                    child.children().forEach {
                        it.removeParent()
                        backGroup?.addView(it)
                    }
                    backGroup?.setNewItemSpace(child.getCurrentSpace())
                }

                is MoreGroup -> {
                    if (child.attrs.removeAllDefaultChild) {
                        moreGroup?.removeAllViews()
                    }
                    child.children().forEach {
                        it.removeParent()
                        moreGroup?.addView(it)
                    }
                    moreGroup?.setNewItemSpace(child.getCurrentSpace())
                }

                else -> {
                    moreGroup?.addView(child, params)
                }
            }
        }
    }

    @IntDef(CONTENT_GRAVITY_CENTER, CONTENT_GRAVITY_START, CONTENT_GRAVITY_END)
    @Retention(AnnotationRetention.SOURCE)
    annotation class ContentGravity

    companion object {
        const val CONTENT_GRAVITY_CENTER = 0
        const val CONTENT_GRAVITY_START = 1
        const val CONTENT_GRAVITY_END = 2
        private var commonClickListener: CommonClickListener? = null
        fun setCommonClickListener(commonClickListener: CommonClickListener) {
            QuickTitleView.commonClickListener = commonClickListener
        }
    }

    interface CommonClickListener {
        fun onBackClick(view: View) {

        }

        fun onTitleClick(view: View) {

        }

        fun onSubTitleClick(view: View) {

        }
    }
}

class BackGroup @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : QuickSpaceLinearLayout(context, attrs, defStyleAttr) {

    internal val attrs =
        AutoGetAttributeHelper.init(context, attrs, R.styleable.BackGroup, BackGroupAttrs())

    internal class BackGroupAttrs(
        val removeAllDefaultChild: Boolean = false,
    )
}

class MoreGroup @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : QuickSpaceLinearLayout(context, attrs, defStyleAttr) {

    internal val attrs =
        AutoGetAttributeHelper.init(context, attrs, R.styleable.MoreGroup, MoreGroupAttrs())

    internal class MoreGroupAttrs(
        val removeAllDefaultChild: Boolean = false,
    )
}

interface QuickTitleThemeI {
    var height: Int?
    var background: Drawable?

    @QuickTitleView.ContentGravity
    var contentGravity: Int?
    var isAbsoluteCenter: Boolean?               //是否绝对居中，title文字在屏幕中央
    var isLinearLayout: Boolean?

    var showLine: Boolean?

    var showBackIcon: Boolean?
    var backIcon: Int?

    var titleStr: String?

    var titleColor: Int?
    var titleBold: Boolean?
    var titleSize: Float?

    var subTitleStr: String?

    var subTitleColor: Int?
    var subTitleBold: Boolean?
    var subTitleSize: Float?

    var titleSpace: Int?

    var backGroupChild: (QuickSpaceLinearLayout.() -> List<View>)?
    var moreGroupChild: (QuickSpaceLinearLayout.() -> List<View>)?

    fun initDataByXml(context: Context) {
        height = height ?: 44.dp
        background = background ?: android.R.color.darker_gray.toDrawable(context)
        contentGravity = contentGravity ?: QuickTitleView.CONTENT_GRAVITY_START
        isLinearLayout = isLinearLayout ?: true
        showLine = showLine ?: false
        showBackIcon = showBackIcon ?: true
        backIcon = backIcon ?: R.drawable.baseline_arrow_back_ios_new_20_white
        titleStr = titleStr ?: ""
        titleColor = titleColor ?: android.R.color.white.toColor(context)
        titleBold = titleBold ?: true
        titleSize = titleSize ?: 18f.sp
        subTitleStr = subTitleStr ?: ""
        subTitleColor = subTitleColor ?: android.R.color.white.toColor(context)
        subTitleBold = subTitleBold ?: false
        subTitleSize = subTitleSize ?: 14f.sp
        titleSpace = titleSpace.nullDefault(0)
        isAbsoluteCenter = isAbsoluteCenter ?: true
    }

    fun with(other: QuickTitleThemeI?): QuickTitleThemeI {
        height = height ?: other?.height
        background = background ?: other?.background
        contentGravity = contentGravity ?: other?.contentGravity
        isAbsoluteCenter = isAbsoluteCenter ?: other?.isAbsoluteCenter
        isLinearLayout = isLinearLayout ?: other?.isLinearLayout
        showLine = showLine ?: other?.showLine
        showBackIcon = showBackIcon ?: other?.showBackIcon
        backIcon = backIcon ?: other?.backIcon
        titleStr = titleStr ?: other?.titleStr
        titleColor = titleColor ?: other?.titleColor
        titleBold = titleBold ?: other?.titleBold
        titleSize = titleSize ?: other?.titleSize
        subTitleStr = subTitleStr ?: other?.subTitleStr
        subTitleColor = subTitleColor ?: other?.subTitleColor
        subTitleBold = subTitleBold ?: other?.subTitleBold
        subTitleSize = subTitleSize ?: other?.subTitleSize
        titleSpace = titleSpace ?: other?.titleSpace
        backGroupChild = backGroupChild ?: other?.backGroupChild
        moreGroupChild = moreGroupChild ?: other?.moreGroupChild
        return this
    }

    fun copy(): QuickTitleThemeI {
        return QuickTitleAttrs().apply {
            height = this@QuickTitleThemeI.height
            background = this@QuickTitleThemeI.background?.mutate()?.copy()
            contentGravity = this@QuickTitleThemeI.contentGravity
            isAbsoluteCenter = this@QuickTitleThemeI.isAbsoluteCenter
            isLinearLayout = this@QuickTitleThemeI.isLinearLayout
            showLine = this@QuickTitleThemeI.showLine
            showBackIcon = this@QuickTitleThemeI.showBackIcon
            backIcon = this@QuickTitleThemeI.backIcon
            titleStr = this@QuickTitleThemeI.titleStr
            titleColor = this@QuickTitleThemeI.titleColor
            titleBold = this@QuickTitleThemeI.titleBold
            titleSize = this@QuickTitleThemeI.titleSize
            subTitleStr = this@QuickTitleThemeI.subTitleStr
            subTitleColor = this@QuickTitleThemeI.subTitleColor
            subTitleBold = this@QuickTitleThemeI.subTitleBold
            subTitleSize = this@QuickTitleThemeI.subTitleSize
            titleSpace = this@QuickTitleThemeI.titleSpace
            backGroupChild = this@QuickTitleThemeI.backGroupChild
            moreGroupChild = this@QuickTitleThemeI.moreGroupChild
        }
    }
}

open class QuickTitleAttrs(
    override var height: Int? = null,
    override var background: Drawable? = null,

    @QuickTitleView.ContentGravity override var contentGravity: Int? = null,
    override var isAbsoluteCenter: Boolean? = null,               //是否绝对居中，title文字在屏幕中央
    override var isLinearLayout: Boolean? = null,

    override var showLine: Boolean? = null,

    override var showBackIcon: Boolean? = null,
    @DrawableRes override var backIcon: Int? = null,

    override var titleStr: String? = null,

    @ColorInt override var titleColor: Int? = null,
    override var titleBold: Boolean? = null,
    override var titleSize: Float? = null,

    override var subTitleStr: String? = null,

    @ColorInt override var subTitleColor: Int? = null,
    override var subTitleBold: Boolean? = null,
    override var subTitleSize: Float? = null,

    override var titleSpace: Int? = null,

    override var backGroupChild: (QuickSpaceLinearLayout.() -> List<View>)? = null,
    override var moreGroupChild: (QuickSpaceLinearLayout.() -> List<View>)? = null,
) : QuickTitleThemeI