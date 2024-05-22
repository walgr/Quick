package com.wpf.app.quickwidget.shadow

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.text.Editable
import android.util.AttributeSet
import androidx.annotation.ColorInt
import androidx.appcompat.widget.AppCompatEditText
import com.wpf.app.quickwidget.shadow.base.ShadowData
import com.wpf.app.quickwidget.shadow.base.ShadowLiveFactory
import com.wpf.app.quickwidget.shadow.base.ShadowView

open class ShadowEditView @JvmOverloads constructor(
    context: Context,
    override val attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    override var key: String = "",
    override var bindTypes: List<ShadowData<out Any>>? = null,
    abilityList: List<ShadowData<out Any>>? = null
) : AppCompatEditText(context, attrs, defStyleAttr), ShadowView {

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
                ShadowLiveFactory.backgroundLiveData,
                ShadowLiveFactory.hintLiveData,
                ShadowLiveFactory.hintTextColorLiveData
            )
        )
    }

    override fun onAttachedToWindow() {
        super<AppCompatEditText>.onAttachedToWindow()
        super<ShadowView>.onAttachedToWindow()
    }

    override fun onDetachedFromWindow() {
        super<AppCompatEditText>.onDetachedFromWindow()
        super<ShadowView>.onDetachedFromWindow()
    }

    override fun setText(text: CharSequence?, type: BufferType?) {
        super<AppCompatEditText>.setText(text, type)
        super<ShadowView>.setText(text, type)
    }

    override fun setTextColor(@ColorInt color: Int) {
        super<AppCompatEditText>.setTextColor(color)
        super<ShadowView>.setTextColor(color)
    }

    override fun getTextColor(): Int {
        return super.getCurrentTextColor()
    }

    override fun setTextColor(colors: ColorStateList?) {
        super<AppCompatEditText>.setTextColor(colors)
        super<ShadowView>.setTextColor(colors)
    }

    override fun getTextColorStateList(): ColorStateList? {
        return super.getTextColors()
    }

    override fun setTextSize(unit: Int, size: Float) {
        super<AppCompatEditText>.setTextSize(unit, size)
        super<ShadowView>.setTextSize(unit, size)
    }

    override fun getTextSize(): Float {
        return super<AppCompatEditText>.getTextSize()
    }

    override fun getText(): Editable? {
        return super<AppCompatEditText>.getText()
    }

    override fun getBackground(): Drawable? {
        return super<AppCompatEditText>.getBackground()
    }

    override fun setBackground(background: Drawable?) {
        super<AppCompatEditText>.setBackground(background)
        super<ShadowView>.setBackground(background)
    }

    override fun isSelected(): Boolean {
        return super<AppCompatEditText>.isSelected()
    }

    override fun setSelected(selected: Boolean) {
        super<AppCompatEditText>.setSelected(selected)
        super<ShadowView>.setSelected(selected)
    }

    override fun setVisibility(visibility: Int) {
        super<AppCompatEditText>.setVisibility(visibility)
        super<ShadowView>.setVisibility(visibility)
    }

    override fun getVisibility(): Int {
        return super<AppCompatEditText>.getVisibility()
    }
}