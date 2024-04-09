package com.wpf.app.quickwidget.title

import android.content.Context
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
import com.wpf.app.quickutil.helper.dp
import com.wpf.app.quickutil.helper.toView
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
    protected var ivBack: ImageView? = null
    protected var tvTitle: TextView? = null
    protected var tvSubTitle: TextView? = null
    protected var moreGroup: QuickSpaceLinearLayout? = null
    protected var line: View? = null

    var clickListener: CommonClickListener? = null

    init {
        orientation = VERTICAL
        R.layout.toolbar_layout.toView(context, this, true)
        minimumHeight = 44.dp(context)
        contentLayout = findViewById(R.id.titleContentLayout)
        titleGroup = findViewById(R.id.titleGroup)
        ivBack = findViewById(R.id.ivBack)
        tvTitle = findViewById(R.id.tvTitle)
        tvSubTitle = findViewById(R.id.tvSubTitle)
        moreGroup = findViewById(R.id.moreGroup)
        line = findViewById(R.id.line)

        if (commonClickListener != null || clickListener != null) {
            ivBack?.setOnClickListener {
                QuickTitleView.commonClickListener?.onBackClick(it)
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
    }

    fun showBack(show: Boolean) {
        ivBack?.isVisible = show
    }

    fun setBackIcon(@DrawableRes iconRes: Int) {
        ivBack?.setImageResource(iconRes)
    }

    open fun setContentGravity(
        @ContentGravity gravity: Int,
        showBackIcon: Boolean = true,
        isLinearLayout: Boolean = true,
        isAbsoluteCenter: Boolean = true,
        space: Int = 0
    ) {
        titleGroup?.updateLayoutParams<ConstraintLayout.LayoutParams> {
            marginStart =
                if (showBackIcon && isLinearLayout) 0 else space
            if (isLinearLayout) {
                this.startToEnd = R.id.ivBack
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

    override fun addView(child: View?, index: Int, params: ViewGroup.LayoutParams?) {
        if (child == null || child.id == R.id.titleContentLayout || child.id == R.id.line) {
            super.addView(child, index, params)
        } else {
            moreGroup?.addView(child, moreGroup!!.childCount, params)
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