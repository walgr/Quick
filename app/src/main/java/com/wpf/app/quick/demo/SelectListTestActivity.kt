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
    QuickActivity(R.layout.activity_select_test, titleName = "选择筛选页") {

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.list1)
    var list1: QuickRecyclerView? = null

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.list2)
    var list2: QuickRecyclerView? = null

    @SuppressLint("NonConstantResourceId")
    @BindData2View(id = R.id.btnClean, helper = ItemClick::class)
    var btnClean = itemClick {
        clean(it)
    }

    override fun initView() {
        val allData = mutableListOf<SelectItem>()
        for (i in 0..10) {
            val parent = SelectItem().apply {
                id = i.toString()
                name = "父"
            }
            allData.add(parent)
            val childList = mutableListOf<SelectItem>()
            for (j in 0..20) {
                childList.add(SelectItem().apply {
                    id = i.toString()
                    name = "子"
                })
            }
            parent.childList = childList
        }
        list1?.setNewData(allData)
        list2?.setNewData(allData[0].childList)
    }

    fun clean(view: View?) {
        list2?.cleanAll()
    }
}