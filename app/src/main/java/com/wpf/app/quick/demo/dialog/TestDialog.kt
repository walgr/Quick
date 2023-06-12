package com.wpf.app.quick.demo.dialog

import android.content.Context
import android.view.Gravity
import android.view.View
import com.wpf.app.quick.annotations.BindData2View
import com.wpf.app.quick.demo.R
import com.wpf.app.quick.demo.model.MyMessage
import com.wpf.app.quickdialog.QuickDialog
import com.wpf.app.quickrecyclerview.helper.List2RecyclerView

/**
 * 自适应高度Dialog
 */
class TestDialog(
    mContext: Context,
    layoutId: Int = R.layout.dialog_test,
) : QuickDialog(mContext, layoutId = layoutId) {

    @BindData2View(id = R.id.list, helper = List2RecyclerView::class)
    val listData = arrayListOf(
        MyMessage(),
        MyMessage(),
        MyMessage()
    )

    override fun initView(view: View?) {

    }

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