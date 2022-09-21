package com.wpf.app.quick.widgets.recyclerview

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.LinearLayoutManager
import com.wpf.app.quick.widgets.recyclerview.listeners.DataSelectOnAdapter
import com.wpf.app.quick.widgets.recyclerview.listeners.OnSelectOnChange
import com.wpf.app.quick.widgets.recyclerview.listeners.SetSelectChange

/**
 * Created by 王朋飞 on 2022/9/5.
 *
 */
open class QuickSelectRecyclerView @JvmOverloads constructor(
    mContext: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : QuickRecyclerView(mContext, attrs, defStyleAttr), DataSelectOnAdapter, SetSelectChange {

    override fun init() {
        layoutManager = LinearLayoutManager(context)
        mQuickAdapter = QuickSelectAdapter()
        mQuickAdapter.mRecyclerView = this
        adapter = mQuickAdapter
    }

    override fun getAdapter() : QuickAdapter {
        return mQuickAdapter
    }

    override fun getSelectAdapter(): QuickSelectAdapter {
        return adapter as QuickSelectAdapter
    }

    override fun setOnSelectChangeListener(onSelectChange: OnSelectOnChange) {
        getSelectAdapter().setOnSelectChangeListener(onSelectChange)
    }

    override fun getOnSelectChangeListener(): OnSelectOnChange? {
        return getSelectAdapter().getOnSelectChangeListener()
    }
}