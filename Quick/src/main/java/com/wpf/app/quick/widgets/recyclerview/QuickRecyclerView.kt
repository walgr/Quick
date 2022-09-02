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
) : RecyclerView(mContext, attrs, defStyleAttr), DataChangeAdapter, DataSelectOnAdapter {

    private lateinit var mQuickAdapter: QuickAdapter

    init {
        init()
    }

    @CallSuper
    protected fun init() {
        layoutManager = LinearLayoutManager(context)
        mQuickAdapter = QuickAdapter()
        adapter = mQuickAdapter
    }

    override fun setOnSelectChange(onSelectChange: OnSelectOnChange) {
        adapter.setOnSelectChange(onSelectChange)
    }

    override fun getOnSelectChange(): OnSelectOnChange? {
        return adapter.getOnSelectChange()
    }

    override fun getAdapter() : QuickAdapter {
        return mQuickAdapter
    }
}