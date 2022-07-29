package com.wpf.app.quick.widgets.recyclerview

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import androidx.annotation.CallSuper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by 王朋飞 on 2022/7/13.
 *
 */
open class QuickRecyclerView @JvmOverloads constructor(
    open val mContext: Context,
    open val attrs: AttributeSet? = null,
    open val defStyleAttr: Int = 0
) : RecyclerView(mContext, attrs, defStyleAttr), DataChangeAdapter {

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

    override fun getAdapter() : QuickAdapter {
        return mQuickAdapter
    }
}