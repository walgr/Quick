package com.wpf.app.quick.widgets.shadow

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatCheckBox

open class ShadowCheckBox @JvmOverloads constructor(
    context: Context,
    override val attrs: AttributeSet? = null,
    override var key: String = "",
    override var bindTypes: Array<Int>? = null,
    abilityList: List<ShadowData<out Any>>? = null
) : AppCompatCheckBox(context, attrs), ShadowView {

    init {
        initShadow(
            context,
            abilityList ?: listOf(
                ShadowLiveFactory.visibilityLiveData,
                ShadowLiveFactory.textLiveData,
                ShadowLiveFactory.textSizeLiveData,
                ShadowLiveFactory.textColorLiveData,
                ShadowLiveFactory.textColorStateListLiveData,
                ShadowLiveFactory.isSelectLiveData,
                ShadowLiveFactory.checkedLiveData,
            )
        )
    }

    override fun onAttachedToWindow() {
        super<AppCompatCheckBox>.onAttachedToWindow()
        super<ShadowView>.onAttachedToWindow()
    }

    override fun onDetachedFromWindow() {
        super<AppCompatCheckBox>.onDetachedFromWindow()
        super<ShadowView>.onDetachedFromWindow()
    }

    override fun setChecked(checked: Boolean) {
        super<AppCompatCheckBox>.setChecked(checked)
        super<ShadowView>.setChecked(checked)
    }

    override fun isChecked(): Boolean {
        return super<AppCompatCheckBox>.isChecked()
    }

    override fun isSelected(): Boolean {
        return super<AppCompatCheckBox>.isSelected()
    }

    override fun setSelected(selected: Boolean) {
        super<AppCompatCheckBox>.setSelected(selected)
        super<ShadowView>.setSelected(selected)
    }

    override fun setText(text: CharSequence?, type: BufferType?) {
        super<AppCompatCheckBox>.setText(text, type)
        super<ShadowView>.setText(text, type)
    }

    override fun getText(): CharSequence {
        return super<AppCompatCheckBox>.getText()
    }

    override fun setTextColor(color: Int) {
        super<AppCompatCheckBox>.setTextColor(color)
        super<ShadowView>.setTextColor(color)
    }

    override fun getTextColor(): Int {
        return super.getCurrentTextColor()
    }

    override fun setTextColor(colors: ColorStateList?) {
        super<AppCompatCheckBox>.setTextColor(colors)
        super<ShadowView>.setTextColor(colors)
    }

    override fun getTextColorStateList(): ColorStateList? {
        return super.getTextColors()
    }

    override fun setTextSize(unit: Int, size: Float) {
        super<AppCompatCheckBox>.setTextSize(unit, size)
        super<ShadowView>.setTextSize(unit, size)
    }

    override fun getTextSize(): Float {
        return super<AppCompatCheckBox>.getTextSize()
    }

    override fun getBackground(): Drawable? {
        return super<AppCompatCheckBox>.getBackground()
    }

    override fun setBackground(background: Drawable?) {
        super<AppCompatCheckBox>.setBackground(background)
        super<ShadowView>.setBackground(background)
    }

    override fun setVisibility(visibility: Int) {
        super<AppCompatCheckBox>.setVisibility(visibility)
        super<ShadowView>.setVisibility(visibility)
    }

    override fun getVisibility(): Int {
        return super<AppCompatCheckBox>.getVisibility()
    }
}