package com.wpf.app.quick.demo.dialog

import android.content.Context
import android.view.Gravity
import android.view.View
import com.wpf.app.quick.demo.R
import com.wpf.app.quickdialog.QuickDialog

/**
 * 自适应高度Dialog
 */
class TestDialog(
    mContext: Context,
    layoutId: Int = R.layout.dialog_test,
) : QuickDialog(mContext, layoutId = layoutId) {

    override fun initView(view: View?) {

    }

    override fun initDialogGravity(): Int {
        return Gravity.BOTTOM
    }

    override fun initDialogMinHeight(): Int {
        return (getScreenHeight() * 0.3f).toInt()
    }

    override fun initDialogMaxHeight(): Int {
        return (getScreenHeight() * 0.8f).toInt()
    }

    override fun initDialogAdaptiveHeight(): Boolean {
        return true
    }
}