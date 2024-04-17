package com.wpf.app.quick.demo.dialog

import android.content.Context
import android.view.Gravity
import com.wpf.app.quick.annotations.bind.BindData2View
import com.wpf.app.quick.demo.R
import com.wpf.app.quick.demo.model.MyMessage
import com.wpf.app.quickdialog.QuickBaseDialog
import com.wpf.app.quickrecyclerview.helper.List2RecyclerView

/**
 * 自适应高度Dialog
 */
class TestDialog(
    mContext: Context,
) : QuickBaseDialog(mContext, layoutId = R.layout.dialog_test) {

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

    override fun initDialogAnimStyle(): Int {
        return R.style.DialogBottomTopAnim
    }

    override fun initDialogAdaptiveHeight(): Boolean {
        return true
    }
}