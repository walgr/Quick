package com.wpf.app.quickrecyclerview

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wpf.app.quickrecyclerview.listeners.DataAdapter
import com.wpf.app.quickrecyclerview.utils.SpaceItemDecoration
import com.wpf.app.quickrecyclerview.utils.SpaceType
import com.wpf.app.quickutil.helper.attribute.AutoGetAttributeHelper

/**
 * Created by 王朋飞 on 2022/7/13.
 *
 */
open class QuickRecyclerView @JvmOverloads constructor(
    mContext: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RecyclerView(mContext, attrs, defStyleAttr), DataAdapter {

    protected var mQuickAdapter: QuickAdapter = QuickAdapter()
    private val attr: QuickRecyclerViewAttr

    init {
        attr = AutoGetAttributeHelper.init(context, attrs, R.styleable.QuickRecyclerView)
        this.initView()
    }

    open fun initView() {
        if (layoutManager == null) {
            layoutManager = LinearLayoutManager(context)
        }
        if (attr.space != null) {
            addItemDecoration(SpaceItemDecoration(attr.space, attr.spaceType, attr.includeFirst, attr.includeLast))
        }
        mQuickAdapter.mRecyclerView = this
        adapter = mQuickAdapter
    }

    override fun getQuickAdapter(): QuickAdapter {
        return mQuickAdapter
    }

    internal class QuickRecyclerViewAttr @JvmOverloads constructor(
        val space: Int? = null,
        val spaceType: Int = SpaceType.Center.type,
        val color: Int? = null,
        val includeFirst: Boolean = false,
        val includeLast: Boolean = false,
    )
}