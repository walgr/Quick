package com.wpf.app.quick.base.widgets.recyclerview

import android.content.Context
import android.util.AttributeSet
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by 王朋飞 on 2022/5/11.
 *  通用列表
 *  支持多类型
 */
open class CommonRecyclerView @JvmOverloads constructor(
    context: Context,
    attributes: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RecyclerView(context, attributes, defStyleAttr) {

    lateinit var mAdapter: CommonAdapter

    init {
        setAdapter()
    }

    fun setAdapter() {
        mAdapter = CommonAdapter()
        adapter = mAdapter
    }
}

@BindingAdapter("setData")
fun setData(list: CommonRecyclerView, newData: List<CommonItemData>?) {
    newData?.let {
        list.mAdapter.setNewData(newData)
    }
}

@BindingAdapter("selectList")
fun selectList(list: CommonRecyclerView, selectList: List<CommonItemData>) {
    list.mAdapter.getData()?.forEach { listData ->
        selectList.find { it.id == listData.id }?.isSelect?.postValue(true)
    }
}

/**
 * 根据id设置选中
 */
@BindingAdapter("selectIdList")
fun selectIdList(list: CommonRecyclerView, selectList: MutableLiveData<List<String>>) {
    list.mAdapter.getData()?.find { listData ->
        selectList.value?.contains(listData.id) ?: false
    }?.isSelect?.postValue(true)
}
