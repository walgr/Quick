package com.wpf.app.quickwork.widget.theme

import android.content.Context

interface QuickDialogThemeBase {
    var curTheme: QuickDialogThemeI?
    fun initDialogTheme(context: Context, theme: QuickDialogThemeI? = null) {
        curTheme = theme ?: defaultTheme ?: QuickDialogTheme()
        curTheme?.initDataInXml(context)
    }

    companion object {
        var defaultTheme: QuickDialogThemeI? = null
            get() = field?.copy()
    }
}

interface QuickDialogThemeI {
    var dialogStyle: Int?
    var canBackgroundClick: Boolean?
    var heightAdaptive: Boolean?
    var widthAdaptive: Boolean?
    var alpha: Float?
    var gravity: Int?
    var animRes: Int?
    var width: Int?
    var height: Int?
    var widthPercent: Float?
    var heightPercent: Float?
    var minWidth: Int?
    var minHeight: Int?
    var maxWidth: Int?
    var maxHeight: Int?

    fun initDataInXml(context: Context) {
        canBackgroundClick = canBackgroundClick ?: false
    }

    fun copy(): QuickDialogThemeI {
        return QuickDialogTheme(
            dialogStyle,
            canBackgroundClick,
            heightAdaptive,
            widthAdaptive,
            alpha,
            gravity,
            animRes,
            width,
            height,
            widthPercent,
            heightPercent,
            minWidth,
            minHeight,
            maxWidth,
            maxHeight
        )
    }
}

open class QuickDialogTheme(
    override var dialogStyle: Int? = null,
    override var canBackgroundClick: Boolean? = null,
    override var heightAdaptive: Boolean? = null,
    override var widthAdaptive: Boolean? = null,
    override var alpha: Float? = null,
    override var gravity: Int? = null,
    override var animRes: Int? = null,
    override var width: Int? = null,
    override var height: Int? = null,
    override var widthPercent: Float? = null,
    override var heightPercent: Float? = null,
    override var minWidth: Int? = null,
    override var minHeight: Int? = null,
    override var maxWidth: Int? = null,
    override var maxHeight: Int? = null,
) : QuickDialogThemeI