package com.wpf.app.quickrecyclerview

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.wpf.app.quickrecyclerview.data.QuickBindData
import com.wpf.app.quickrecyclerview.data.QuickItemData
import com.wpf.app.quickrecyclerview.data.QuickViewData
import com.wpf.app.quickrecyclerview.holder.QuickViewBindingHolder
import com.wpf.app.quickrecyclerview.holder.QuickViewHolder
import com.wpf.app.quickrecyclerview.listeners.DataAdapter
import com.wpf.app.quickrecyclerview.listeners.QuickAdapterListener
import com.wpf.app.quickutil.other.asTo

/**
 * Created by 王朋飞 on 2022/7/13.
 *
 */
open class QuickAdapter : RecyclerView.Adapter<QuickViewHolder<QuickItemData>>(), DataAdapter {

    var mDataList: MutableList<QuickItemData>? = null

    private var mQuickAdapterListener: QuickAdapterListener<QuickItemData>? = null
    var mRecyclerView: RecyclerView? = null

    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int
    ): QuickViewHolder<QuickItemData> {
        mDataList?.find {
            it.viewType == viewType
        }?.let { findData ->
            var holder: QuickViewHolder<QuickItemData>? = null
            if (findData is QuickBindData) {
                findData.beforeCreateHolder(viewGroup)
                if (findData.isDealBinding) {
                    holder = QuickViewBindingHolder<QuickItemData, ViewDataBinding>(
                        viewGroup,
                        findData.layoutId,
                        findData.layoutViewInContext
                    )
                }
            }
            if (holder == null && findData is QuickViewData) {
                holder = QuickViewHolder(viewGroup, findData.layoutId, findData.layoutViewInContext)
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
        viewHolder.itemPosition = position
        viewHolder.onBindViewHolder(this, mDataList!![position], position)
    }

    override fun getItemViewType(position: Int): Int {
        return mDataList?.get(position)?.viewType ?: super.getItemViewType(position)
    }

    override fun getItemCount(): Int {
        return mDataList?.size ?: 0
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
}