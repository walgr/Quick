package com.wpf.app.quick.widgets.shadow

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatToggleButton

open class ShadowToggleButton @JvmOverloads constructor(
    context: Context,
    override val attrs: AttributeSet? = null,
    override var key: String = "",
    abilityList: List<ShadowData<out Any>>? = null
) : AppCompatToggleButton(context, attrs), ShadowView {

    init {
        initShadow(
            context,
            abilityList ?: listOf(
                ShadowLive.textLiveData,
                ShadowLive.textSizeLiveData,
                ShadowLive.textColorLiveData,
                ShadowLive.textColorStateListLiveData,
                ShadowLive.checkedLiveData
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
}