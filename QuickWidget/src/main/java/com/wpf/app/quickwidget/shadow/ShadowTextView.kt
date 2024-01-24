package com.wpf.app.quickwidget.shadow

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.annotation.ColorInt
import androidx.appcompat.widget.AppCompatTextView
import com.wpf.app.quickwidget.shadow.base.ShadowData
import com.wpf.app.quickwidget.shadow.base.ShadowLiveFactory
import com.wpf.app.quickwidget.shadow.base.ShadowView

open class ShadowTextView @JvmOverloads constructor(
    context: Context,
    override val attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    override var key: String = "",
    override var bindTypes: List<ShadowData<out Any>>? = null,
    abilityList: List<ShadowData<out Any>>? = null
) : AppCompatTextView(context, attrs, defStyleAttr), ShadowView {

    init {
        this.initShadow(
            context,
            abilityList ?: listOf(
                ShadowLiveFactory.visibilityLiveData,
                ShadowLiveFactory.textLiveData,
                ShadowLiveFactory.textSizeLiveData,
                ShadowLiveFactory.textColorLiveData,
                ShadowLiveFactory.textColorStateListLiveData,
                ShadowLiveFactory.isSelectLiveData,
                ShadowLiveFactory.backgroundLiveData
            )
        )
    }

    override fun onAttachedToWindow() {
        super<AppCompatTextView>.onAttachedToWindow()
        super<ShadowView>.onAttachedToWindow()
    }

    override fun onDetachedFromWindow() {
        super<AppCompatTextView>.onDetachedFromWindow()
        super<ShadowView>.onDetachedFromWindow()
    }

    override fun setText(text: CharSequence?, type: BufferType?) {
        super<AppCompatTextView>.setText(text, type)
        super<ShadowView>.setText(text, type)
    }

    override fun setTextColor(@ColorInt color: Int) {
        super<AppCompatTextView>.setTextColor(color)
        super<ShadowView>.setTextColor(color)
    }

    override fun getTextColor(): Int {
        return super.getCurrentTextColor()
    }

    override fun setTextColor(colors: ColorStateList?) {
        super<AppCompatTextView>.setTextColor(colors)
        super<ShadowView>.setTextColor(colors)
    }

    override fun getTextColorStateList(): ColorStateList? {
        return super.getTextColors()
    }

    override fun setTextSize(unit: Int, size: Float) {
        super<AppCompatTextView>.setTextSize(unit, size)
        super<ShadowView>.setTextSize(unit, size)
    }

    override fun getTextSize(): Float {
        return super<AppCompatTextView>.getTextSize()
    }

    override fun getText(): CharSequence {
        return super<AppCompatTextView>.getText()
    }

    override fun getBackground(): Drawable? {
        return super<AppCompatTextView>.getBackground()
    }

    override fun setBackground(background: Drawable?) {
        super<AppCompatTextView>.setBackground(background)
        super<ShadowView>.setBackground(background)
    }

    override fun isSelected(): Boolean {
        return super<AppCompatTextView>.isSelected()
    }

    override fun setSelected(selected: Boolean) {
        super<AppCompatTextView>.setSelected(selected)
        super<ShadowView>.setSelected(selected)
    }

    override fun setVisibility(visibility: Int) {
        super<AppCompatTextView>.setVisibility(visibility)
        super<ShadowView>.setVisibility(visibility)
    }

    override fun getVisibility(): Int {
        return super<AppCompatTextView>.getVisibility()
    }
}