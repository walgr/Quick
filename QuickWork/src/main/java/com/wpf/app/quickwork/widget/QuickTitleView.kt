package com.wpf.app.quickwork.widget

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
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
import com.wpf.app.quickutil.helper.dp
import com.wpf.app.quickutil.helper.toView
import com.wpf.app.quickutil.other.printLog
import com.wpf.app.quickwork.R
import kotlin.math.max

class QuickTitleView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : LinearLayout(context, attrs, defStyleAttr) {

    private var contentLayout: ConstraintLayout? = null
    private var titleGroup: ViewGroup? = null
    private var ivBack: ImageView? = null
    private var tvTitle: TextView? = null
    private var tvSubTitle: TextView? = null
    private var moreGroup: ViewGroup? = null
    private var line: View? = null

    private lateinit var style: QuickTitleStyle

    private var commonClickListener: CommonClickListener? = null

    init {
        orientation = VERTICAL
        var commonStyle: QuickTitleStyle? = null
        commonStyleBuilder?.let {
            commonStyle = QuickTitleStyle()
            it.invoke(commonStyle!!, context)
        }
        this.style =
            AutoGetAttributeHelper.init(context, attrs, R.styleable.QuickTitleView, commonStyle)
        R.layout.toolbar_layout.toView(context, this, true)
        minimumHeight = 44.dp(context)
        contentLayout = findViewById(R.id.contentLayout)
        titleGroup = findViewById(R.id.titleGroup)
        ivBack = findViewById(R.id.ivBack)
        tvTitle = findViewById(R.id.tvTitle)
        tvSubTitle = findViewById(R.id.tvSubTitle)
        moreGroup = findViewById(R.id.moreGroup)
        line = findViewById(R.id.line)

        this.style.initDataInXml(context)
        if (background == null) {
            this.style.background?.let {
                setBackgroundResource(it)
            }
        }
        setStyle(this.style)
        if (QuickTitleView.commonClickListener != null || commonClickListener != null) {
            ivBack?.setOnClickListener {
                QuickTitleView.commonClickListener?.onBackClick(it)
                    ?: commonClickListener?.onBackClick(it)
            }

            tvTitle?.setOnClickListener {
                QuickTitleView.commonClickListener?.onTitleClick(it)
                    ?: commonClickListener?.onTitleClick(it)
            }

            tvSubTitle?.setOnClickListener {
                QuickTitleView.commonClickListener?.onSubTitleClick(it)
                    ?: commonClickListener?.onSubTitleClick(it)
            }
        }
    }

    private fun setStyle(style: QuickTitleStyle) {
        style.apply {
            showBackIcon?.let {
                showBack(it)
            }
            backIcon?.let {
                setBackIcon(it)
            }

            titleStr?.let {
                showTitle(it.isNotEmpty())
                setTitle(it)
            }
            titleColor?.let {
                setTitleColor(it)
            }
            titleSize?.let {
                setTitleSize(it.toFloat())
            }
            titleBold?.let {
                setTitleIsBold(it)
            }

            subTitleStr?.let {
                showSubTitle(it.isNotEmpty())
                setSubTitle(it)
            }
            subTitleColor?.let {
                setSubTitleColor(it)
            }
            subTitleSize?.let {
                setSubTitleSize(it.toFloat())
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
                setContentGravity(it)
            }
            moreGroup?.updateLayoutParams<MarginLayoutParams> { marginEnd = space ?: 0 }
        }
    }

    fun showBack(show: Boolean) {
        ivBack?.isVisible = show
    }

    fun setBackIcon(@DrawableRes iconRes: Int) {
        ivBack?.setImageResource(iconRes)
    }

    fun setContentGravity(@ContentGravity gravity: Int) {
        titleGroup?.updateLayoutParams<ConstraintLayout.LayoutParams>() {
            val space = style.space ?: 0
            marginStart =
                if (style.showBackIcon == true && style.isLinearLayout == true) 0 else space
            if (style.isLinearLayout == true) {
                this.startToEnd = R.id.ivBack
                this.endToStart = R.id.moreGroup
            } else {
                this.startToStart = ConstraintLayout.LayoutParams.PARENT_ID
                this.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
            }
            if (gravity == CONTENT_GRAVITY_CENTER) {
                horizontalBias = 0.5f
                if (style.isLinearLayout == true && style.isAbsoluteCenter == true) {
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

    fun showTitle(show: Boolean) {
        tvTitle?.isVisible = show
    }

    fun setTitle(title: String) {
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

    fun setSubTitle(title: String) {
        tvSubTitle?.text = title
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

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)
        if (isInEditMode && style.isAbsoluteCenter == true && titleGroup != null && ivBack != null && moreGroup != null) {
            style.apply {
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
        if (child == null || child.id == R.id.contentLayout || child.id == R.id.line) {
            super.addView(child, index, params)
        } else {
            moreGroup?.addView(child, moreGroup!!.childCount, params)
        }
    }

    fun setCommonClickListener(commonClickListener: CommonClickListener) {
        this.commonClickListener = commonClickListener
    }

    companion object {
        const val CONTENT_GRAVITY_CENTER = 0
        const val CONTENT_GRAVITY_START = 1
        const val CONTENT_GRAVITY_END = 2

        private var commonClickListener: CommonClickListener? = null
        fun setCommonClickListener(commonClickListener: CommonClickListener) {
            QuickTitleView.commonClickListener = commonClickListener
        }

        private var commonStyleBuilder: (QuickTitleStyle.(context: Context) -> Unit)? = null
        fun commonStyleBuilder(builder: QuickTitleStyle.(context: Context) -> Unit) {
            commonStyleBuilder = builder
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

    @IntDef(CONTENT_GRAVITY_CENTER, CONTENT_GRAVITY_START, CONTENT_GRAVITY_END)
    @Retention(AnnotationRetention.SOURCE)
    annotation class ContentGravity

    data class QuickTitleStyle(
        @DrawableRes var background: Int? = null,

        @ContentGravity var contentGravity: Int? = null,
        var isAbsoluteCenter: Boolean? = null,               //是否绝对居中，title文字在屏幕中央
        var isLinearLayout: Boolean? = null,

        var showLine: Boolean? = null,

        var showBackIcon: Boolean? = null,
        @DrawableRes var backIcon: Int? = null,

        var titleStr: String? = null,

        @ColorInt var titleColor: Int? = null,
        var titleBold: Boolean? = null,
        var titleSize: Int? = null,

        var subTitleStr: String? = null,

        @ColorInt var subTitleColor: Int? = null,
        var subTitleBold: Boolean? = null,
        var subTitleSize: Int? = null,

        var space: Int? = null,
        var titleSpace: Int? = null
    ) {
        fun initDataInXml(context: Context) {
            if (this.background == null) {
                this.background = android.R.color.darker_gray
            }
            if (this.contentGravity == null) {
                this.contentGravity = CONTENT_GRAVITY_START
            }
            if (this.isLinearLayout == null) {
                this.isLinearLayout = true
            }
            if (this.showLine == null) {
                this.showLine = false
            }
            if (this.showBackIcon == null) {
                this.showBackIcon = true
            }
            if (this.backIcon == null) {
                this.backIcon = R.drawable.baseline_arrow_back_ios_new_20_white
            }
            if (this.titleStr == null) {
                this.titleStr = ""
            }
            if (this.titleColor == null) {
                this.titleColor = Color.WHITE
            }
            if (this.titleBold == null) {
                this.titleBold = true
            }
            if (this.titleSize == null) {
                this.titleSize = 18.dp(context)
            }
            if (this.subTitleStr == null) {
                this.subTitleStr = ""
            }
            if (this.subTitleColor == null) {
                this.subTitleColor = Color.WHITE
            }
            if (this.subTitleBold == null) {
                this.subTitleBold = false
            }
            if (this.subTitleSize == null) {
                this.subTitleSize = 14.dp(context)
            }
            if (this.space == null) {
                this.space = 16.dp(context)
            }
            if (this.titleSpace == null) {
                this.titleSpace = 0
            }
            if (this.isAbsoluteCenter == null) {
                this.isAbsoluteCenter = true
            }
        }
    }
}