package com.wpf.app.quickrecyclerview

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.wpf.app.quickrecyclerview.data.QuickAbilityData
import com.wpf.app.quickrecyclerview.data.QuickBindData
import com.wpf.app.quickrecyclerview.data.QuickFooterData
import com.wpf.app.quickrecyclerview.data.QuickHeaderData
import com.wpf.app.quickrecyclerview.data.QuickItemData
import com.wpf.app.quickrecyclerview.data.QuickViewData
import com.wpf.app.quickrecyclerview.holder.QuickViewBindingHolder
import com.wpf.app.quickrecyclerview.holder.QuickViewHolder
import com.wpf.app.quickrecyclerview.listeners.DataAdapter
import com.wpf.app.quickrecyclerview.listeners.QuickAdapterListener
import com.wpf.app.quickutil.helper.generic.asTo
import com.wpf.app.quickutil.helper.generic.forceTo
import com.wpf.app.quickutil.helper.generic.nullDefault

/**
 * Created by 王朋飞 on 2022/7/13.
 *
 */
open class QuickAdapter : RecyclerView.Adapter<QuickViewHolder<QuickItemData>>(), DataAdapter {
    private var mRecyclerView: RecyclerView? = null
    private var mQuickAdapterListener: QuickAdapterListener<QuickItemData>? = null

    internal var mDataList: MutableList<QuickItemData>? = null
    internal var allDataList: MutableList<QuickItemData>? = null
    val headerViews = mutableListOf<QuickHeaderData>()
    val footerViews = mutableListOf<QuickFooterData>()

    val extraParameter: LinkedHashMap<String, Any> = linkedMapOf()

    fun getDataByViewType(viewType: Int): QuickItemData? {
        return (mDataList?.find {
            it.viewType == viewType
        } ?: headerViews.find { it.viewType == viewType }
        ?: footerViews.find { it.viewType == viewType })
    }

    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int,
    ): QuickViewHolder<QuickItemData> {
        getDataByViewType(viewType)?.let { findData ->
            var holder: QuickViewHolder<QuickItemData>? = null
            if (findData is QuickBindData) {
                findData.setAdapter(this)
                findData.beforeAdapterCreateHolder(viewGroup)
                if (findData is QuickAbilityData && findData.bindingClass != null) {
                    holder = QuickViewBindingHolder<QuickItemData, ViewDataBinding>(
                        viewGroup,
                        findData.layoutId,
                        findData.layoutView,
                        findData.layoutViewInViewGroup,
                        bindVBClass = findData.bindingClass!!.forceTo()
                    )
                }
            }
            if (holder == null && findData is QuickViewData) {
                holder = QuickViewHolder(
                    viewGroup,
                    findData.layoutId,
                    findData.layoutView,
                    findData.layoutViewInViewGroup
                )
            }
            holder?.mQuickAdapter = this
            holder?.let {
                if (findData is QuickBindData) {
                    findData.beforeHolderOnCreateHolder(holder)
                }
                holder.onCreateViewHolder(holder.itemView)
                if (findData is QuickBindData) {
                    findData.afterHolderOnCreateHolder(holder)
                }
            }
            return holder as QuickViewHolder<QuickItemData>
        }
        return null!!
    }

    override fun onBindViewHolder(viewHolder: QuickViewHolder<QuickItemData>, position: Int) {
        val realData: QuickItemData? =
            if (headerViews.isNotEmpty() && position < headerViews.size) {
                headerViews[position]
            } else if (position < headerViews.size + mDataList?.size.nullDefault(0)) {
                mDataList?.get(position - headerViews.size)
            } else {
                footerViews[position - (headerViews.size + mDataList?.size.nullDefault(0))]
            }
        viewHolder.onBindViewHolder(this, realData, position)
    }

    override fun getItemViewType(position: Int): Int {
        if (position < 0) return -1
        if (headerViews.isNotEmpty() && position < headerViews.size) {
            return headerViews[position].viewType
        } else if (position < headerViews.size + mDataList?.size.nullDefault(0)) {
            mDataList?.get(position - headerViews.size)?.let {
                if (it is QuickAbilityData) {
                    it.viewType = it.initViewType(position)
                }
                return it.viewType
            }
        } else if (position < headerViews.size + mDataList?.size.nullDefault(0) + footerViews.size) {
            return footerViews[position - (headerViews.size + mDataList?.size.nullDefault(0))].viewType
        }
        return super.getItemViewType(position)
    }

    override fun getItemCount(): Int {
        return headerViews.size + mDataList?.size.nullDefault(0) + footerViews.size
    }

    fun getQuickAdapterListener(): QuickAdapterListener<QuickItemData>? {
        return mQuickAdapterListener
    }

    fun setQuickAdapterListener(listener: QuickAdapterListener<out QuickItemData>?) {
        this.mQuickAdapterListener = listener?.asTo()
    }

    override fun getQuickAdapter(): QuickAdapter {
        return this
    }

    fun setRecyclerView(recyclerView: RecyclerView) {
        this.mRecyclerView = recyclerView
    }

    fun getRecyclerView(): RecyclerView? {
        return mRecyclerView
    }
}