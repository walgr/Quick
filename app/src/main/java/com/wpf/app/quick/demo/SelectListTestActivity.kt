package com.wpf.app.quick.demo

import android.annotation.SuppressLint
import android.view.View
import com.wpf.app.quick.activity.QuickActivity
import com.wpf.app.quick.annotations.BindData2View
import com.wpf.app.quick.annotations.BindView
import com.wpf.app.quick.helper.binddatahelper.ItemClick
import com.wpf.app.quick.demo.model.SelectItem
import com.wpf.app.quick.widgets.recyclerview.QuickRecyclerView
import com.wpf.app.quickbind.interfaces.itemClick

/**
 * Created by 王朋飞 on 2022/7/8.
 */
class SelectListTestActivity :
    QuickActivity(R.layout.activity_recyclerview_test, titleName = "选择列表页") {
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.list)
    var list: QuickRecyclerView? = null

    @SuppressLint("NonConstantResourceId")
    @BindData2View(id = R.id.btnClean, helper = ItemClick::class)
    var btnClean = itemClick {
        clean(it)
    }

    @SuppressLint("NonConstantResourceId")
    @BindData2View(id = R.id.btnAdd, helper = ItemClick::class)
    var btnAdd = itemClick {
        addMessage(it)
    }

    override fun initView() {

    }

    fun clean(view: View?) {
        list?.cleanAll()
    }

    fun addMessage(view: View?) {
        list?.addData(SelectItem())
        list?.adapter?.notifyItemInserted(list?.size() ?: 0)
    }
}