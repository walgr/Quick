package com.wpf.app.quick.widgets.recyclerview

import android.content.Context
import android.util.AttributeSet
import androidx.annotation.CallSuper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wpf.app.quick.widgets.recyclerview.listeners.DataChangeAdapter
import com.wpf.app.quick.widgets.recyclerview.listeners.DataSelectOnAdapter
import com.wpf.app.quick.widgets.recyclerview.listeners.OnSelectOnChange

/**
 * Created by 王朋飞 on 2022/7/13.
 *
 */
open class QuickRecyclerView @JvmOverloads constructor(
    mContext: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RecyclerView(mContext, attrs, defStyleAttr), DataChangeAdapter {

    open lateinit var mQuickAdapter: QuickAdapter

    init {
        init()
    }

    open fun init() {
        layoutManager = LinearLayoutManager(context)
        mQuickAdapter = QuickAdapter()
        mQuickAdapter.mRecyclerView = this
        adapter = mQuickAdapter
    }

    override fun getAdapter() : QuickAdapter {
        return mQuickAdapter
    }
}