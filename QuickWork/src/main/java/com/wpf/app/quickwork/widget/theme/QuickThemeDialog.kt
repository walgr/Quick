package com.wpf.app.quickwork.widget.theme

import android.content.Context
import androidx.annotation.StyleRes
import com.wpf.app.quickutil.ability.base.QuickAbility
import com.wpf.app.quick.ability.QuickDialog

open class QuickThemeDialog @JvmOverloads constructor(
    context: Context,
    @StyleRes themeId: Int = 0,
    theme: QuickDialogThemeI? = null,
    abilityList: MutableList<QuickAbility> = mutableListOf(),
) : QuickDialog(
    context, themeId, abilityList
), QuickDialogThemeBase {

    override var curTheme: QuickDialogThemeI? = null

    init {
        initDialogTheme(context, theme)
    }

    override fun canDialogBackgroundClick(): Boolean {
        return curTheme?.canBackgroundClick ?: super.canDialogBackgroundClick()
    }

    override fun initDialogAdaptiveHeight(): Boolean {
        return curTheme?.heightAdaptive ?: super.initDialogAdaptiveHeight()
    }

    override fun initDialogAdaptiveWidth(): Boolean {
        return curTheme?.widthAdaptive ?: super.initDialogAdaptiveWidth()
    }

    override fun initDialogAlpha(): Float {
        return curTheme?.alpha ?: super.initDialogAlpha()
    }

    override fun initDialogGravity(): Int {
        return curTheme?.gravity ?: super.initDialogGravity()
    }

    override fun initDialogAnimStyle(): Int {
        return curTheme?.animStyleRes ?: super.initDialogAnimStyle()
    }

    override fun initDialogWidth(): Int {
        return curTheme?.width ?: super.initDialogWidth()
    }

    override fun initDialogHeight(): Int {
        return curTheme?.height ?: super.initDialogHeight()
    }

    override fun initDialogWidthPercent(): Float {
        return curTheme?.widthPercent ?: super.initDialogWidthPercent()
    }

    override fun initDialogHeightPercent(): Float {
        return curTheme?.heightPercent ?: super.initDialogHeightPercent()
    }

    override fun initDialogMinWidth(): Int {
        return curTheme?.minWidth ?: super.initDialogMinWidth()
    }

    override fun initDialogMinHeight(): Int {
        return curTheme?.minHeight ?: super.initDialogMinHeight()
    }

    override fun initDialogMaxWidth(): Int {
        return curTheme?.maxWidth ?: super.initDialogMaxWidth()
    }

    override fun initDialogMaxHeight(): Int {
        return curTheme?.maxHeight ?: super.initDialogMaxHeight()
    }
}
