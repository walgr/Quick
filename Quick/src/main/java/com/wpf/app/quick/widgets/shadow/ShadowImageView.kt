package com.wpf.app.quick.widgets.shadow

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView

open class ShadowImageView @JvmOverloads constructor(
    context: Context,
    override val attrs: AttributeSet? = null,
    override var key: String = "",
    abilityList: List<ShadowData<out Any>>? = null
) : AppCompatImageView(context, attrs), ShadowView {

    init {
        initShadow(
            context,
            abilityList ?: listOf(
                ShadowLiveFactory.visibilityLiveData,
                ShadowLiveFactory.imageLiveData,
                ShadowLiveFactory.isSelectLiveData,
                ShadowLiveFactory.backgroundLiveData
            )
        )
    }

    override fun onAttachedToWindow() {
        super<AppCompatImageView>.onAttachedToWindow()
        super<ShadowView>.onAttachedToWindow()
    }

    override fun onDetachedFromWindow() {
        super<AppCompatImageView>.onDetachedFromWindow()
        super<ShadowView>.onDetachedFromWindow()
    }

    override fun setImageDrawable(drawable: Drawable?) {
        super<AppCompatImageView>.setImageDrawable(drawable)
        super<ShadowView>.setImageDrawable(drawable)
    }

    override fun setBackground(background: Drawable?) {
        super<AppCompatImageView>.setBackground(background)
        super<ShadowView>.setBackground(background)
    }

    override fun getDrawable(): Drawable? {
        return super<AppCompatImageView>.getDrawable()
    }

    override fun getBackground(): Drawable? {
        return super<AppCompatImageView>.getBackground()
    }

    override fun isSelected(): Boolean {
        return super<AppCompatImageView>.isSelected()
    }

    override fun setSelected(selected: Boolean) {
        super<AppCompatImageView>.setSelected(selected)
        super<ShadowView>.setSelected(selected)
    }

    override fun setVisibility(visibility: Int) {
        super<AppCompatImageView>.setVisibility(visibility)
        super<ShadowView>.setVisibility(visibility)
    }

    override fun getVisibility(): Int {
        return super<AppCompatImageView>.getVisibility()
    }
}