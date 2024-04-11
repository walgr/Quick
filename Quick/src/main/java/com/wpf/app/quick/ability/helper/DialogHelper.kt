package com.wpf.app.quick.ability.helper

import android.content.Context
import android.view.View
import androidx.annotation.LayoutRes
import androidx.annotation.StyleRes
import com.wpf.app.quick.ability.QuickDialog
import com.wpf.app.quick.ability.ex.contentView
import com.wpf.app.quickutil.other.context
import com.wpf.app.quickutil.run.RunOnContext

fun Any.dialog(
    themeId: Int = 0,
    @LayoutRes layoutId: Int = 0,
    layoutView: View? = null,
    layoutViewInContext: RunOnContext<View>? = null,
    canBackgroundClick: Boolean? = null,
    @StyleRes dialogStyle: Int? = null,
    width: Int? = null,
    height: Int? = null,
    widthPercent: Float? = null,
    heightPercent: Float? = null,
    minWidth: Int? = null,
    minHeight: Int? = null,
    maxWidth: Int? = null,
    maxHeight: Int? = null,
    gravity: Int? = null,
    animRes: Int? = null,
    alpha: Float? = null,
    widthAdaptive: Boolean? = null,
    heightAdaptive: Boolean? = null,
    builder: (QuickDialog.() -> Unit)? = null,
): QuickDialog {
    val mContext: Context = context()!!
    val dialog = object : QuickDialog(
        mContext,
        themeId = themeId,
        abilityList = contentView(layoutId, layoutView, layoutViewInContext)
    ) {

        override fun canDialogBackgroundClick(): Boolean {
            return canBackgroundClick ?: super.canDialogBackgroundClick()
        }

        override fun initDialogStyle(): Int {
            return dialogStyle ?: super.initDialogStyle()
        }

        override fun initDialogAdaptiveHeight(): Boolean {
            return heightAdaptive ?: super.initDialogAdaptiveHeight()
        }

        override fun initDialogAdaptiveWidth(): Boolean {
            return widthAdaptive ?: super.initDialogAdaptiveWidth()
        }

        override fun initDialogAlpha(): Float {
            return alpha ?: super.initDialogAlpha()
        }

        override fun initDialogGravity(): Int {
            return gravity ?: super.initDialogGravity()
        }

        override fun initDialogAnim(): Int {
            return animRes ?: super.initDialogAnim()
        }

        override fun initDialogWidth(): Int {
            return width ?: super.initDialogWidth()
        }

        override fun initDialogHeight(): Int {
            return height ?: super.initDialogHeight()
        }

        override fun initDialogWidthPercent(): Float {
            return widthPercent ?: super.initDialogWidthPercent()
        }

        override fun initDialogHeightPercent(): Float {
            return heightPercent ?: super.initDialogHeightPercent()
        }

        override fun initDialogMinWidth(): Int {
            return minWidth ?: super.initDialogMinWidth()
        }

        override fun initDialogMinHeight(): Int {
            return minHeight ?: super.initDialogMinHeight()
        }

        override fun initDialogMaxWidth(): Int {
            return maxWidth ?: super.initDialogMaxWidth()
        }

        override fun initDialogMaxHeight(): Int {
            return maxHeight ?: super.initDialogMaxHeight()
        }
    }
    builder?.invoke(dialog)
    return dialog
}