package com.wpf.app.quickdialog.listeners

import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.annotation.StyleRes

/**
 * Created by 王朋飞 on 2022/6/17.
 */
interface DialogSize : QuickContext {

    fun initDialogHeightPercent(): Float {
        return NO_SET.toFloat()
    }

    //指定初始高度 不能低于最低 不能超过最高
    fun initDialogHeight(): Int {
        return WindowManager.LayoutParams.WRAP_CONTENT
    }

    //最高高度
    fun initDialogMaxHeight(): Int {
        return WindowManager.LayoutParams.MATCH_PARENT
    }

    //最低高度
    fun initDialogMinHeight(): Int {
        return WindowManager.LayoutParams.WRAP_CONTENT
    }

    //高度自适应
    fun initDialogAdaptiveHeight(): Boolean {
        return false
    }

    fun initDialogWidthPercent(): Float {
        return NO_SET.toFloat()
    }

    //指定初始宽度  不能低于最低 不能超过最高
    fun initDialogWidth(): Int {
        return WindowManager.LayoutParams.MATCH_PARENT
    }

    //最高宽度
    fun initDialogMaxWidth(): Int {
        return WindowManager.LayoutParams.MATCH_PARENT
    }

    //最低宽度
    fun initDialogMinWidth(): Int {
        return WindowManager.LayoutParams.WRAP_CONTENT
    }

    //高度自适应
    fun initDialogAdaptiveWidth(): Boolean {
        return false
    }

    //dialog背景下是否可点击
    fun canDialogBackgroundClick(): Boolean {
        return false
    }

    fun initDialogAlpha(): Float {
        return 0.35f
    }

    fun initDialogGravity(): Int {
        return Gravity.CENTER
    }

    @StyleRes
    fun initDialogAnimStyle(): Int {
        return NO_SET
    }

    fun getView(): View?
    fun getWindow(): Window?

    fun getNewWidth(): Int
    fun getNewHeight(): Int

    companion object {
        const val NO_SET = 0
    }
}