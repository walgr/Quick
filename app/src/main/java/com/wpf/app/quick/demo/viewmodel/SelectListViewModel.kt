package com.wpf.app.quick.demo.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.wpf.app.quick.demo.databinding.ActivitySelectTestBinding
import com.wpf.app.quick.demo.model.ParentSelectItem
import com.wpf.app.quick.demo.model.SelectItem
import com.wpf.app.quick.demo.model.SelectResultItem
import com.wpf.app.quick.utils.LogUtil
import com.wpf.app.quick.viewmodel.QuickBindingViewModel
import com.wpf.app.quick.widgets.recyclerview.data.QuickChildSelectData
import com.wpf.app.quick.widgets.recyclerview.listeners.OnSelectCallback
import com.wpf.app.quick.widgets.recyclerview.listeners.OnSelectOnChange

/**
 * Created by 王朋飞 on 2022/9/16.
 *
 */
class SelectListViewModel: QuickBindingViewModel<ActivitySelectTestBinding>() {

    val selectList: MutableLiveData<List<QuickChildSelectData>> = MutableLiveData()

    override fun onBindingCreated(mViewBinding: ActivitySelectTestBinding?) {
        mViewBinding?.selectList?.mOnSelectCallback = object : OnSelectCallback {
            override fun onSelectResult(selectResult: List<QuickChildSelectData>?) {
                LogUtil.e("选择了${selectResult}")
                selectList.value = selectResult
                mViewBinding?.selectResult?.setNewData(selectResult?.map {
                    SelectResultItem(it.isSelect, it.parent, it.id, it.name)
                })
            }
        }
        mViewBinding?.selectResult?.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        mViewBinding?.selectList?.bindResult(mViewBinding.selectResult)
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
        getViewBinding()?.selectList?.setData(allData)
    }
}