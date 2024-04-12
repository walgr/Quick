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
    private val attr: QuickRecyclerViewAttr by lazy {
        AutoGetAttributeHelper.init(context, attrs, R.styleable.QuickRecyclerView)
    }

    init {
        this.initView()
        dealAttrs()
    }

    open fun initView() {
        if (layoutManager == null) {
            layoutManager = LinearLayoutManager(context)
        }
        mQuickAdapter.setRecyclerView(this)
        adapter = mQuickAdapter
    }

    private fun dealAttrs() {
        if (attr.space != null) {
            setSpace(attr.space!!, attr.spaceType, attr.includeFirst, attr.includeLast)
        }
    }

    open fun setSpace(space: Int, spaceType: Int = SpaceType.Center.type, includeFirst: Boolean = false, includeLast: Boolean = false) {
        addItemDecoration(SpaceItemDecoration(space, spaceType, includeFirst, includeLast))
    }

    override fun getQuickAdapter(): QuickAdapter {
        return mQuickAdapter
    }

    open class QuickRecyclerViewAttr @JvmOverloads constructor(
        val space: Int? = null,
        val spaceType: Int = SpaceType.Center.type,
        val includeFirst: Boolean = false,
        val includeLast: Boolean = false,
    )
}