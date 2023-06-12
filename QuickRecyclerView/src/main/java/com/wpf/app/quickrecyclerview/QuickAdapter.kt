package com.wpf.app.quickrecyclerview

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.wpf.app.quickrecyclerview.annotations.BindHolder
import com.wpf.app.quickrecyclerview.data.QuickBindData
import com.wpf.app.quickrecyclerview.data.QuickItemData
import com.wpf.app.quickrecyclerview.data.QuickViewDataBinding
import com.wpf.app.quickrecyclerview.holder.BindBindingHolder
import com.wpf.app.quickrecyclerview.holder.QuickViewBindingHolder
import com.wpf.app.quickrecyclerview.holder.QuickViewHolder
import com.wpf.app.quickrecyclerview.listeners.DataAdapter
import com.wpf.app.quickrecyclerview.listeners.QuickAdapterListener

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
            if (findData is QuickViewDataBinding<*>) {
                val holderAnnotationClass =
                    findData::class.java.getAnnotation(BindBindingHolder::class.java)
                holderAnnotationClass?.let {
                    val bindingHolderCls = it.value.java.getConstructor(ViewGroup::class.java)
                    val bindingHolder = bindingHolderCls.newInstance(viewGroup)
                            as QuickViewBindingHolder<QuickViewDataBinding<out ViewDataBinding>, out ViewDataBinding>
                    bindingHolder.onCreateViewHolder(bindingHolder.itemView)
                    return bindingHolder as QuickViewHolder<QuickItemData>
                }
            }

            var holder: QuickViewHolder<out QuickItemData>? = null
            if (findData is QuickBindData) {
                holder = QuickViewHolder(
                    viewGroup,
                    findData.layoutId,
                    findData.layoutView,
                    findData.layoutViewInContext
                )
            } else {
                val holderAnnotation = findData::class.java.getAnnotation(BindHolder::class.java)
                    ?: throw RuntimeException("当前数据类未使用HolderClass注解指定ViewHolder")
                try {
                    val holderCls = holderAnnotation.value
                    val holderConstructor = holderCls.java.getConstructor(ViewGroup::class.java)
                    holder =
                        holderConstructor?.newInstance(viewGroup) as QuickViewHolder<out QuickItemData>
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            holder?.let {
                holder.onCreateViewHolder(holder.itemView)
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
        this.mQuickAdapterListener = listener as QuickAdapterListener<QuickItemData>
    }

    override fun getQuickAdapter(): QuickAdapter {
        return this
    }
}