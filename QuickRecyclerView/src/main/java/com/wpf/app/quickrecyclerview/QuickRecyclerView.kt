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

    override fun getQuickAdapter() : QuickAdapter {
        return mQuickAdapter
    }
}