package com.wpf.app.quickwidget.selectview

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.LinearLayoutManager
import com.wpf.app.quickrecyclerview.QuickAdapter
import com.wpf.app.quickrecyclerview.QuickRecyclerView
import com.wpf.app.quickwidget.selectview.listeners.DataSelectOnAdapter
import com.wpf.app.quickwidget.selectview.listeners.OnSelectOnChange
import com.wpf.app.quickwidget.selectview.listeners.SetSelectChange

/**
 * Created by 王朋飞 on 2022/9/5.
 *
 */
open class QuickSelectRecyclerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : QuickRecyclerView(context, attrs, defStyleAttr), DataSelectOnAdapter, SetSelectChange {

    override fun initView() {
        if (layoutManager == null) {
            layoutManager = LinearLayoutManager(context)
        }
        mQuickAdapter = QuickSelectAdapter()
        mQuickAdapter.setRecyclerView(this)
        adapter = mQuickAdapter
    }

    override fun getQuickAdapter(): QuickAdapter {
        return mQuickAdapter
    }

    override fun getSelectAdapter(): QuickSelectAdapter {
        return getQuickAdapter() as QuickSelectAdapter
    }

    override fun setOnSelectChangeListener(onSelectChange: OnSelectOnChange) {
        getSelectAdapter().setOnSelectChangeListener(onSelectChange)
    }

    override fun getOnSelectChangeListener(): OnSelectOnChange? {
        return getSelectAdapter().getOnSelectChangeListener()
    }
}