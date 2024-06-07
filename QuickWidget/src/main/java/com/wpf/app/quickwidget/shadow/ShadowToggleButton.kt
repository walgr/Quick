package com.wpf.app.quickwidget.shadow

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatToggleButton
import com.wpf.app.quickwidget.shadow.base.ShadowData
import com.wpf.app.quickwidget.shadow.base.ShadowLiveFactory
import com.wpf.app.quickwidget.shadow.base.ShadowView

@Suppress("LeakingThis")
open class ShadowToggleButton @JvmOverloads constructor(
    context: Context,
    override val attrs: AttributeSet? = null,
    override var key: String = "",
    override var bindTypes: List<ShadowData<out Any>>? = null,
    abilityList: List<ShadowData<out Any>>? = null
) : AppCompatToggleButton(context, attrs), ShadowView {

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
                ShadowLiveFactory.checkedLiveData
            )
        )
    }

    override fun onAttachedToWindow() {
        super<AppCompatToggleButton>.onAttachedToWindow()
        super<ShadowView>.onAttachedToWindow()
    }

    override fun onDetachedFromWindow() {
        super<AppCompatToggleButton>.onDetachedFromWindow()
        super<ShadowView>.onDetachedFromWindow()
    }

    override fun setChecked(checked: Boolean) {
        super<AppCompatToggleButton>.setChecked(checked)
        super<ShadowView>.setChecked(checked)
    }

    override fun isChecked(): Boolean {
        return super<AppCompatToggleButton>.isChecked()
    }

    override fun isSelected(): Boolean {
        return super<AppCompatToggleButton>.isSelected()
    }

    override fun setSelected(selected: Boolean) {
        super<AppCompatToggleButton>.setSelected(selected)
        super<ShadowView>.setSelected(selected)
    }

    override fun setText(text: CharSequence?, type: BufferType?) {
        super<AppCompatToggleButton>.setText(text, type)
        super<ShadowView>.setText(text, type)
    }

    override fun getText(): CharSequence {
        return super<AppCompatToggleButton>.getText()
    }


    override fun setTextColor(color: Int) {
        super<AppCompatToggleButton>.setTextColor(color)
        super<ShadowView>.setTextColor(color)
    }

    override fun getTextColor(): Int {
        return super.getCurrentTextColor()
    }

    override fun setTextColor(colors: ColorStateList?) {
        super<AppCompatToggleButton>.setTextColor(colors)
        super<ShadowView>.setTextColor(colors)
    }

    override fun getTextColorStateList(): ColorStateList? {
        return super.getTextColors()
    }

    override fun setTextSize(unit: Int, size: Float) {
        super<AppCompatToggleButton>.setTextSize(unit, size)
        super<ShadowView>.setTextSize(unit, size)
    }

    override fun getTextSize(): Float {
        return super<AppCompatToggleButton>.getTextSize()
    }

    override fun getBackground(): Drawable? {
        return super<AppCompatToggleButton>.getBackground()
    }

    override fun setBackground(background: Drawable?) {
        super<AppCompatToggleButton>.setBackground(background)
        super<ShadowView>.setBackground(background)
    }

    override fun setVisibility(visibility: Int) {
        super<AppCompatToggleButton>.setVisibility(visibility)
        super<ShadowView>.setVisibility(visibility)
    }

    override fun getVisibility(): Int {
        return super<AppCompatToggleButton>.getVisibility()
    }
}