package com.wpf.app.quick.demo

import android.annotation.SuppressLint
import android.view.View
import com.google.gson.Gson
import com.wpf.app.quick.activity.QuickActivity
import com.wpf.app.quick.annotations.BindData2View
import com.wpf.app.quick.annotations.BindView
import com.wpf.app.quick.demo.model.ParentSelectItem
import com.wpf.app.quick.helper.binddatahelper.ItemClick
import com.wpf.app.quick.demo.model.SelectItem
import com.wpf.app.quick.utils.GsonHelper
import com.wpf.app.quick.utils.LogUtil
import com.wpf.app.quick.widgets.recyclerview.data.QuickChildSelectData
import com.wpf.app.quick.widgets.recyclerview.listeners.OnSelectCallback
import com.wpf.app.quick.widgets.selectview.QuickMultistageSelectView
import com.wpf.app.quickbind.interfaces.itemClick

/**
 * Created by 王朋飞 on 2022/7/8.
 */
class SelectListTestActivity :
    QuickActivity(R.layout.activity_select_test, titleName = "选择筛选页") {

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.selectList)
    var selectList: QuickMultistageSelectView? = null

    @SuppressLint("NonConstantResourceId")
    @BindData2View(id = R.id.btnClean, helper = ItemClick::class)
    var btnClean = itemClick {
        clean(it)
    }

    override fun initView() {
        loadData()
    }

    private fun loadData() {
        val allData = mutableListOf<ParentSelectItem>()
        for (i in 0 until 10) {
            val parentItem = ParentSelectItem().apply {
                id = i.toString()
                name = "父"
            }
            allData.add(parentItem)
            val childList = mutableListOf<SelectItem>()
            for (j in 0 until 20) {
                childList.add(SelectItem().apply {
                    parent = parentItem
                    id = j.toString()
                    name = "子"
                })
            }
            parentItem.childList = childList
        }
        selectList?.setData(allData)
        selectList?.mOnSelectCallback = object : OnSelectCallback {
            override fun onSelectResult(selectResult: List<QuickChildSelectData>?) {
                LogUtil.e("选择了${selectResult}")
            }

        }
    }

    fun clean(view: View?) {
//        list2?.cleanAll()
    }
}