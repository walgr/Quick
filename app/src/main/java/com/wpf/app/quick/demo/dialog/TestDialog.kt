package com.wpf.app.quick.demo.dialog

import android.content.Context
import android.view.Gravity
import com.wpf.app.quick.annotations.bind.BindData2View
import com.wpf.app.quick.demo.R
import com.wpf.app.quick.demo.model.MyMessage
import com.wpf.app.quickdialog.QuickDialog
import com.wpf.app.quickrecyclerview.helper.List2RecyclerView

/**
 * 自适应高度Dialog
 */
class TestDialog(
    mContext: Context,
) : QuickDialog(mContext, layoutId = R.layout.dialog_test) {

    @BindData2View(id = R.id.list, helper = List2RecyclerView::class)
    val listData = arrayListOf(
        MyMessage(msg = "1"),
        MyMessage(msg = "20"),
        MyMessage(msg = "300"),
        MyMessage(msg = "1"),
        MyMessage(msg = "20"),
        MyMessage(msg = "300"),
        MyMessage(msg = "1"),
        MyMessage(msg = "20"),
        MyMessage(msg = "300"),
        MyMessage(msg = "1"),
        MyMessage(msg = "20"),
        MyMessage(msg = "300"),
        MyMessage(msg = "1"),
        MyMessage(msg = "20"),
        MyMessage(msg = "300"),
    )

    override fun initDialogGravity(): Int {
        return Gravity.BOTTOM
    }

    override fun initDialogMinHeight(): Int {
        return (getScreenHeight() * 0.3f).toInt()
    }

    override fun initDialogMaxHeight(): Int {
        return (getScreenHeight() * 0.9f).toInt()
    }

    override fun initDialogAdaptiveHeight(): Boolean {
        return true
    }
}