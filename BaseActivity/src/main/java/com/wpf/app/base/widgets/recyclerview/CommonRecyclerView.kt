package com.wpf.app.base.widgets.recyclerview

import android.content.Context
import android.util.AttributeSet
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by 王朋飞 on 2022/5/11.
 *  通用列表
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

@BindingAdapter("selectList")
fun selectList(list: CommonRecyclerView, selectList: List<CommonItemData>) {
    list.mAdapter.getData()?.forEach { listData ->
        selectList.find { it.id ==  listData.id}?.isSelect = true
    }
}