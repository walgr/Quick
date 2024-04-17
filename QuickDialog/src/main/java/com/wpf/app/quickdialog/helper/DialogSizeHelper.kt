package com.wpf.app.quickdialog.helper

import android.view.Window
import android.view.WindowManager
import com.wpf.app.quickdialog.listeners.DialogSize
import com.wpf.app.quickdialog.minAndMaxLimit.MinAndMaxLimit
import com.wpf.app.quickutil.other.asTo

/**
 * Created by 王朋飞 on 2022/6/17.
 */
object DialogSizeHelper {

    fun dealSize(dialog: DialogSize, dialogWidth: Int, dialogHeight: Int) {
        if (dialog.getWindow() == null) return
        val window: Window = dialog.getWindow()!!
        val windowParams: WindowManager.LayoutParams = window.attributes
        windowParams.gravity = dialog.initDialogGravity()
        windowParams.dimAmount = dialog.initDialogAlpha()
        if (dialog.canDialogBackgroundClick()) {
            //背景点击透传
            windowParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
        }
        if (dialog.getNewWidth() != DialogSize.NO_SET || dialog.getNewHeight() != DialogSize.NO_SET) {
            if (dialog.getNewWidth() != DialogSize.NO_SET) {
                windowParams.width = dealDialogWidth(
                    dialog,
                    if (dialog.initDialogWidthPercent() != DialogSize.NO_SET.toFloat()) (dialog.getNewWidth() * dialog.initDialogWidthPercent()).toInt() else dialogWidth
                )
            }
            if (dialog.getNewHeight() != DialogSize.NO_SET) {
                windowParams.height = dealDialogHeight(
                    dialog,
                    if (dialog.initDialogHeightPercent() != DialogSize.NO_SET.toFloat()) (dialog.getNewHeight() * dialog.initDialogHeightPercent()).toInt() else dialogHeight
                )
            }
        } else {
            if ((dialog.initDialogAdaptiveWidth() || dialog.initDialogWidth() == WindowManager.LayoutParams.WRAP_CONTENT) && dialog.getView() != null && dialog.initDialogWidthPercent() == DialogSize.NO_SET.toFloat()) {
                dialog.getView()!!.post {
                    windowParams.width = dealDialogWidth(dialog, dialog.getView()!!.width)
                    if (!dialog.initDialogAdaptiveHeight()) {
                        window.attributes = windowParams
                    }
                }
            } else {
                windowParams.width = dealDialogWidth(
                    dialog,
                    if (dialog.initDialogWidthPercent() != DialogSize.NO_SET.toFloat()) (dialog.getScreenWidth() * dialog.initDialogWidthPercent()).toInt() else dialogWidth
                )
            }
            if ((dialog.initDialogAdaptiveHeight() || dialog.initDialogHeight() == WindowManager.LayoutParams.WRAP_CONTENT) && dialog.getView() != null && dialog.initDialogHeightPercent() == DialogSize.NO_SET.toFloat()) {
                if (dialog.getView() is MinAndMaxLimit) {
                    val minAndMaxLimit = dialog.getView().asTo<MinAndMaxLimit>()
                    minAndMaxLimit?.maxHeight = dialog.initDialogMaxHeight()
                    minAndMaxLimit?.getFirstChild()?.post {
                        windowParams.height = dealDialogHeight(dialog, minAndMaxLimit.getFirstChild()!!.height)
                        if (!dialog.initDialogAdaptiveHeight()) {
                            window.attributes = windowParams
                        }
                    }
                } else {
                    dialog.getView()!!.post {
                        windowParams.height = dealDialogHeight(dialog, dialog.getView()!!.height)
                        window.attributes = windowParams
                    }
                }
            } else {
                windowParams.height = dealDialogHeight(
                    dialog,
                    if (dialog.initDialogHeightPercent() != DialogSize.NO_SET.toFloat()) (dialog.getScreenHeight() * dialog.initDialogHeightPercent()).toInt() else dialogHeight
                )
            }
        }
        window.attributes = windowParams
    }

    private fun dealDialogHeight(dialog: DialogSize, curHeight: Int): Int {
        var curHeightNew = curHeight
        if (curHeightNew == WindowManager.LayoutParams.MATCH_PARENT) {
            curHeightNew = dialog.initDialogMaxHeight()
            if (curHeightNew == WindowManager.LayoutParams.MATCH_PARENT) {
                curHeightNew = dialog.initDialogHeight()
            }
            return if (curHeightNew > 0) {
                dealDialogHeight(dialog, curHeightNew)
            } else curHeightNew
        }
        if (curHeightNew == WindowManager.LayoutParams.WRAP_CONTENT) return curHeightNew
        if (dialog.initDialogMaxHeight() == WindowManager.LayoutParams.MATCH_PARENT
            && dialog.initDialogMinHeight() == WindowManager.LayoutParams.WRAP_CONTENT
        ) return curHeightNew
        var maxHeight: Int = dialog.initDialogMaxHeight()
        if (maxHeight == WindowManager.LayoutParams.MATCH_PARENT) {
            maxHeight = dialog.getScreenHeight()
        }
        return if (curHeightNew > maxHeight) {
            maxHeight
        } else curHeightNew.coerceAtLeast(dialog.initDialogMinHeight())
    }

    private fun dealDialogWidth(dialog: DialogSize, curWidth: Int): Int {
        if (curWidth == WindowManager.LayoutParams.MATCH_PARENT) return curWidth
        if (curWidth == WindowManager.LayoutParams.WRAP_CONTENT) return curWidth
        if (dialog.initDialogMaxWidth() == WindowManager.LayoutParams.MATCH_PARENT
            && dialog.initDialogMinWidth() == WindowManager.LayoutParams.WRAP_CONTENT
        ) return curWidth
        var maxWidth: Int = dialog.initDialogMaxWidth()
        if (maxWidth == WindowManager.LayoutParams.MATCH_PARENT) {
            maxWidth = dialog.getScreenWidth()
        }
        return if (curWidth > maxWidth) {
            maxWidth
        } else curWidth.coerceAtLeast(dialog.initDialogMinWidth())
    }
}