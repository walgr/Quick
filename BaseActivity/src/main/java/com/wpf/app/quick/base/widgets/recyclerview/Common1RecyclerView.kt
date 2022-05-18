package com.wpf.app.quick.base.widgets.recyclerview

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by 王朋飞 on 2022/5/11.
 *  通用列表
 *  支持多类型
 */
open class Common1RecyclerView<T: CommonItemData> @JvmOverloads constructor(
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