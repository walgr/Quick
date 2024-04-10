package com.wpf.app.quickwork.widget

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.core.view.updateLayoutParams
import com.wpf.app.quickutil.helper.*
import com.wpf.app.quickutil.helper.attribute.AutoGetAttributeHelper
import com.wpf.app.quickutil.widget.bold
import com.wpf.app.quickwidget.R
import com.wpf.app.quickwidget.title.QuickTitleView

open class QuickThemeTitle @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    theme: QuickTitleTheme? = null,
) : QuickTitleView(context, attrs, defStyleAttr) {

    protected var curTheme: QuickTitleTheme = AutoGetAttributeHelper.init(
        context, attrs, R.styleable.QuickTitleView, theme ?: commonTheme?.copy()
    )
    protected var childTheme: QuickTitleChildTheme? = null

    init {
        this.curTheme.initDataInXml(context)
        if (background == null) {
            this.curTheme.background?.let {
                setBackgroundResource(it)
            }
        }
        this.childTheme = commonChildTheme?.copy()
        setTheme(this.curTheme)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        if (moreGroup?.childCount != 0 && commonChildTheme?.space != null) {
            moreGroup?.setNewItemSpace(commonChildTheme?.space!!)
        }
    }

    open fun setTheme(style: QuickTitleTheme) {
        style.apply {
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

    open fun dealChildViewCommonStyle(view: View) {
        commonChildTheme?.run {
            when (view) {
                is TextView -> {
                    titleColor?.let {
                        view.setTextColor(it)
                    }
                    titleBold?.let {
                        view.bold(it)
                    }
                    titleSize?.let {
                        view.setTextSize(TypedValue.COMPLEX_UNIT_PX, it)
                    }
                }

                is ImageView -> {
                    if (imgWidth != null && imgHeight != null) {
                        layoutParams = (layoutParams ?: wrapLayoutParams()).reset(imgWidth!!, imgHeight!!)
                    } else {

                    }
                }

                else -> {

                }
            }
        }
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)
        if (isInEditMode && curTheme.isAbsoluteCenter == true && titleGroup != null && ivBack != null && moreGroup != null) {
            curTheme.apply {
                val minL = if (showBackIcon == true && isLinearLayout == true) ivBack!!.width else (space ?: 0)
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

    companion object {

        var commonTheme: QuickTitleTheme? = null

        fun commonThemeBuilder(
            context: Context, builder: QuickTitleTheme.(context: Context) -> Unit,
        ) {
            commonTheme = QuickTitleTheme()
            builder.invoke(commonTheme!!, context)
        }

        var commonChildTheme: QuickTitleChildTheme? = null
        fun childThemeBuilder(
            context: Context, builder: QuickTitleChildTheme.(context: Context) -> Unit,
        ) {
            commonChildTheme = QuickTitleChildTheme()
            builder.invoke(commonChildTheme!!, context)
        }
    }

    data class QuickTitleChildTheme(
        @ColorInt var titleColor: Int? = null,
        var titleBold: Boolean? = null,
        var titleSize: Float? = null,

        var imgWidth: Int? = null,
        var imgHeight: Int? = null,

        var space: Int? = null,
    )

    data class QuickTitleTheme(
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
        var titleSize: Float? = null,

        var subTitleStr: String? = null,

        @ColorInt var subTitleColor: Int? = null,
        var subTitleBold: Boolean? = null,
        var subTitleSize: Float? = null,

        var space: Int? = null,
        var titleSpace: Int? = null,
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
                this.titleColor = android.R.color.white.toColor(context)
            }
            if (this.titleBold == null) {
                this.titleBold = true
            }
            if (this.titleSize == null) {
                this.titleSize = 18.dpF(context)
            }
            if (this.subTitleStr == null) {
                this.subTitleStr = ""
            }
            if (this.subTitleColor == null) {
                this.subTitleColor = android.R.color.white.toColor(context)
            }
            if (this.subTitleBold == null) {
                this.subTitleBold = false
            }
            if (this.subTitleSize == null) {
                this.subTitleSize = 14.dpF(context)
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