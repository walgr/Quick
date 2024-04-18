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
import androidx.core.view.marginEnd
import androidx.core.view.updateLayoutParams
import com.wpf.app.quickutil.helper.attribute.AutoGetAttributeHelper
import com.wpf.app.quickutil.helper.copy
import com.wpf.app.quickutil.helper.dp
import com.wpf.app.quickutil.helper.sp
import com.wpf.app.quickutil.helper.toColor
import com.wpf.app.quickutil.helper.toDrawable
import com.wpf.app.quickutil.helper.toView
import com.wpf.app.quickutil.widget.wishLayoutParams
import com.wpf.app.quickwidget.R
import com.wpf.app.quickwidget.group.QuickSpaceLinearLayout
import kotlin.math.max

open class QuickTitleView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : LinearLayout(context, attrs, defStyleAttr) {

    protected var contentLayout: ViewGroup? = null
    protected var titleGroup: ViewGroup? = null
    protected var backLayout: ViewGroup? = null
    protected var ivBack: ImageView? = null
    protected var tvTitle: TextView? = null
    protected var tvSubTitle: TextView? = null
    private var moreGroup: QuickSpaceLinearLayout? = null
    protected var line: View? = null

    var clickListener: CommonClickListener? = null

    init {
        orientation = VERTICAL
        R.layout.toolbar_layout.toView(context, this, true)
        minimumHeight = 44.dp
        contentLayout = findViewById(R.id.titleContentLayout)
        titleGroup = findViewById(R.id.titleGroup)
        backLayout = findViewById(R.id.backLayout)
        ivBack = findViewById(R.id.ivBack)
        tvTitle = findViewById(R.id.tvTitle)
        tvSubTitle = findViewById(R.id.tvSubTitle)
        moreGroup = findViewById(R.id.moreGroup)
        line = findViewById(R.id.line)

        if (commonClickListener != null || clickListener != null) {
            ivBack?.setOnClickListener {
                commonClickListener?.onBackClick(it)
                    ?: clickListener?.onBackClick(it)
            }

            tvTitle?.setOnClickListener {
                commonClickListener?.onTitleClick(it)
                    ?: clickListener?.onTitleClick(it)
            }

            tvSubTitle?.setOnClickListener {
                commonClickListener?.onSubTitleClick(it)
                    ?: commonClickListener?.onSubTitleClick(it)
            }
        }
        this.initAttrsInXml(attrs)
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
                    space = space ?: 0
                )
            }
            moreGroup?.updateLayoutParams<MarginLayoutParams> { marginEnd = space ?: 0 }
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
        space: Int = 0,
    ) {
        titleGroup?.updateLayoutParams<ConstraintLayout.LayoutParams> {
            marginStart =
                if (showBackIcon && isLinearLayout) 0 else space
            if (isLinearLayout) {
                this.startToEnd = R.id.backLayout
                this.endToStart = R.id.moreGroup
            } else {
                this.startToStart = ConstraintLayout.LayoutParams.PARENT_ID
                this.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
            }
            if (gravity == CONTENT_GRAVITY_CENTER) {
                horizontalBias = 0.5f
                if (isLinearLayout && isAbsoluteCenter) {
                    post {
                        val leftViewW = ivBack!!.width + marginStart
                        val rightViewW = moreGroup!!.width + moreGroup!!.marginEnd
                        val maxW = max(leftViewW, rightViewW)
                        val isDealLeft = maxW == rightViewW
                        titleGroup?.setPadding(
                            paddingLeft + if (isDealLeft) max(0, maxW - leftViewW) else 0,
                            paddingTop,
                            paddingRight + if (!isDealLeft) max(0, maxW - rightViewW) else 0,
                            paddingBottom
                        )
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
                    if (showBackIcon == true && isLinearLayout == true) ivBack!!.width else (space
                        ?: 0)
                val minE = (space ?: 0) + if (isLinearLayout == true) moreGroup!!.width else 0
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
            moreGroup?.addView(child, params)
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

    var space: Int?
    var titleSpace: Int?

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
        space = space ?: 16.dp
        titleSpace = titleSpace ?: 0
        isAbsoluteCenter = isAbsoluteCenter ?: true
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
            space = this@QuickTitleThemeI.space
            titleSpace = this@QuickTitleThemeI.titleSpace
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

    override var space: Int? = null,
    override var titleSpace: Int? = null,
) : QuickTitleThemeI