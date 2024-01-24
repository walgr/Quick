package com.wpf.app.quickrecyclerview

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wpf.app.quickrecyclerview.listeners.DataAdapter

/**
 * Created by 王朋飞 on 2022/7/13.
 *
 */
open class QuickRecyclerView @JvmOverloads constructor(
    mContext: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RecyclerView(mContext, attrs, defStyleAttr), DataAdapter {

     protected val mQuickAdapter: QuickAdapter = QuickAdapter()

    init {
        this.initView()
    }

    open fun initView() {
        if (layoutManager == null) {
            layoutManager = LinearLayoutManager(context)
        }
        mQuickAdapter.mRecyclerView = this
        adapter = mQuickAdapter
    }

    override fun getQuickAdapter(): QuickAdapter {
        return mQuickAdapter
    }
}