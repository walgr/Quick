package com.wpf.app.quick.ability.helper

import android.content.Context
import android.view.Gravity
import android.view.View
import androidx.annotation.LayoutRes
import androidx.annotation.StyleRes
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback
import com.wpf.app.quick.ability.QuickBottomSheetDialog
import com.wpf.app.quick.ability.QuickBottomSheetDialogFragment
import com.wpf.app.quick.ability.QuickDialog
import com.wpf.app.quick.ability.QuickDialogFragment
import com.wpf.app.quick.ability.ex.contentView
import com.wpf.app.quickutil.other.context
import com.wpf.app.quickutil.run.RunOnContext

open class QuickBottomSheetDialogFragmentTemp(
    @LayoutRes layoutId: Int = 0,
    layoutView: View? = null,
    layoutViewInContext: RunOnContext<View>? = null,
    private val canBackgroundClick: Boolean? = null,
    private val width: Int? = null,
    private val height: Int? = null,
    private val widthPercent: Float? = null,
    private val heightPercent: Float? = null,
    private val minWidth: Int? = null,
    private val minHeight: Int? = null,
    private val maxWidth: Int? = null,
    private val maxHeight: Int? = null,
    private val gravity: Int? = null,
    private val animRes: Int? = null,
    private val alpha: Float? = null,
    private val widthAdaptive: Boolean? = null,
    private val heightAdaptive: Boolean? = null,
    private val hideAble: Boolean? = null,
    private val canScroll: Boolean? = null,
    private val peekHeight: Int? = null,
    private val defaultSheetState: Int? = null,
) : QuickBottomSheetDialogFragment(
    abilityList = contentView(
        layoutId, layoutView, layoutViewInContext
    )
) {

    override fun initSheetState(): Int {
        return defaultSheetState ?: super.initSheetState()
    }

    override fun hideAble(): Boolean {
        return hideAble ?: super.hideAble()
    }

    override fun initPeekHeight(): Int {
        return peekHeight ?: super.initPeekHeight()
    }

    override fun canScroll(): Boolean {
        return canScroll ?: super.canScroll()
    }

    override fun canDialogBackgroundClick(): Boolean {
        return canBackgroundClick ?: super.canDialogBackgroundClick()
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
        return gravity ?: Gravity.BOTTOM
    }

    override fun initDialogAnimStyle(): Int {
        return animRes ?: super.initDialogAnimStyle()
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

fun Any.bottomSheetDialogFragment(
    @LayoutRes layoutId: Int = 0,
    layoutView: View? = null,
    layoutViewInContext: RunOnContext<View>? = null,
    canBackgroundClick: Boolean? = null,
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
    hideAble: Boolean? = null,
    canScroll: Boolean? = null,
    peekHeight: Int? = null,
    skipCollapsed: Boolean = false,
    defaultSheetState: Int? = BottomSheetBehavior.STATE_EXPANDED,
    callback: BottomSheetCallback? = null,
    cancelable: Boolean = true,
    cancelableTouchOutside: Boolean = true,
    builder: (QuickBottomSheetDialogFragment.() -> Unit)? = null,
): QuickBottomSheetDialogFragment {
    val dialog = QuickBottomSheetDialogFragmentTemp(
        layoutId,
        layoutView,
        layoutViewInContext,
        canBackgroundClick,
        width,
        height,
        widthPercent,
        heightPercent,
        minWidth,
        minHeight,
        maxWidth,
        maxHeight,
        gravity,
        animRes,
        alpha,
        widthAdaptive,
        heightAdaptive,
        hideAble,
        canScroll,
        peekHeight,
        defaultSheetState
    )
    dialog.onDialogShow {
        setCancelable(cancelable)
        setCanceledOnTouchOutside(cancelableTouchOutside)
        dialog.getBehavior()?.skipCollapsed = skipCollapsed
        callback?.let {
            dialog.getBehavior()?.addBottomSheetCallback(it)
        }
    }
    builder?.invoke(dialog)
    return dialog
}

fun Any.bottomSheetDialog(
    @StyleRes themeId: Int = 0,
    @LayoutRes layoutId: Int = 0,
    layoutView: View? = null,
    layoutViewInContext: RunOnContext<View>? = null,
    canBackgroundClick: Boolean? = null,
    width: Int? = null,
    height: Int? = null,
    widthPercent: Float? = null,
    heightPercent: Float? = null,
    minWidth: Int? = null,
    minHeight: Int? = null,
    maxWidth: Int? = null,
    maxHeight: Int? = null,
    animRes: Int? = null,
    alpha: Float? = null,
    widthAdaptive: Boolean? = null,
    heightAdaptive: Boolean? = null,
    hideAble: Boolean? = null,
    canScroll: Boolean? = null,
    peekHeight: Int? = null,
    skipCollapsed: Boolean = false,
    defaultSheetState: Int? = BottomSheetBehavior.STATE_EXPANDED,
    callback: BottomSheetCallback? = null,
    cancelable: Boolean = true,
    cancelableTouchOutside: Boolean = true,
    builder: (QuickBottomSheetDialog.() -> Unit)? = null,
): QuickBottomSheetDialog {
    val mContext: Context = context()!!
    val dialog = object : QuickBottomSheetDialog(
        mContext,
        themeId = themeId,
        abilityList = contentView(layoutId, layoutView, layoutViewInContext)
    ) {

        override fun initSheetState(): Int {
            return defaultSheetState ?: super.initSheetState()
        }

        override fun hideAble(): Boolean {
            return hideAble ?: super.hideAble()
        }

        override fun initPeekHeight(): Int {
            return peekHeight ?: super.initPeekHeight()
        }

        override fun canScroll(): Boolean {
            return canScroll ?: super.canScroll()
        }

        override fun canDialogBackgroundClick(): Boolean {
            return canBackgroundClick ?: super.canDialogBackgroundClick()
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
            return Gravity.BOTTOM
        }

        override fun initDialogAnimStyle(): Int {
            return animRes ?: super.initDialogAnimStyle()
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
    dialog.onDialogShow {
        setCancelable(cancelable)
        setCanceledOnTouchOutside(cancelableTouchOutside)
        dialog.behavior.skipCollapsed = skipCollapsed
        callback?.let {
            dialog.behavior.addBottomSheetCallback(it)
        }
    }
    builder?.invoke(dialog)
    return dialog
}


open class QuickDialogFragmentTemp(
    @LayoutRes layoutId: Int = 0,
    layoutView: View? = null,
    layoutViewInContext: RunOnContext<View>? = null,
    private val canBackgroundClick: Boolean? = null,
    private val width: Int? = null,
    private val height: Int? = null,
    private val widthPercent: Float? = null,
    private val heightPercent: Float? = null,
    private val minWidth: Int? = null,
    private val minHeight: Int? = null,
    private val maxWidth: Int? = null,
    private val maxHeight: Int? = null,
    private val gravity: Int? = null,
    private val animRes: Int? = null,
    private val alpha: Float? = null,
    private val widthAdaptive: Boolean? = null,
    private val heightAdaptive: Boolean? = null,
) : QuickDialogFragment(abilityList = contentView(layoutId, layoutView, layoutViewInContext)) {
    override fun canDialogBackgroundClick(): Boolean {
        return canBackgroundClick ?: super.canDialogBackgroundClick()
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

    override fun initDialogAnimStyle(): Int {
        return animRes ?: super.initDialogAnimStyle()
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

fun Any.dialogFragment(
    @LayoutRes layoutId: Int = 0,
    layoutView: View? = null,
    layoutViewInContext: RunOnContext<View>? = null,
    canBackgroundClick: Boolean? = null,
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
    cancelable: Boolean = true,
    cancelableTouchOutside: Boolean = true,
    builder: (QuickDialogFragment.() -> Unit)? = null,
): QuickDialogFragment {
    val dialog = QuickDialogFragmentTemp(
        layoutId,
        layoutView,
        layoutViewInContext,
        canBackgroundClick,
        width,
        height,
        widthPercent,
        heightPercent,
        minWidth,
        minHeight,
        maxWidth,
        maxHeight,
        gravity,
        animRes,
        alpha,
        widthAdaptive,
        heightAdaptive
    )
    dialog.onDialogShow {
        setCancelable(cancelable)
        setCanceledOnTouchOutside(cancelableTouchOutside)
    }
    builder?.invoke(dialog)
    return dialog
}

fun Any.dialog(
    @StyleRes themeId: Int = 0,
    @LayoutRes layoutId: Int = 0,
    layoutView: View? = null,
    layoutViewInContext: RunOnContext<View>? = null,
    canBackgroundClick: Boolean? = null,
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
    cancelable: Boolean = true,
    cancelableTouchOutside: Boolean = true,
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

        override fun initDialogAnimStyle(): Int {
            return animRes ?: super.initDialogAnimStyle()
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
    dialog.onDialogShow {
        setCancelable(cancelable)
        setCanceledOnTouchOutside(cancelableTouchOutside)
    }
    builder?.invoke(dialog)
    return dialog
}