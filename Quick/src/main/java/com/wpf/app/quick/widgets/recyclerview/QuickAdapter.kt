package com.wpf.app.quick.widgets.recyclerview

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.wpf.app.quick.widgets.recyclerview.annotations.BindHolder
import com.wpf.app.quick.widgets.recyclerview.data.QuickBindData
import com.wpf.app.quick.widgets.recyclerview.data.QuickItemData
import com.wpf.app.quick.widgets.recyclerview.data.QuickViewDataBinding
import com.wpf.app.quick.widgets.recyclerview.holder.BindBindingHolder
import com.wpf.app.quick.widgets.recyclerview.holder.QuickViewBindingHolder
import com.wpf.app.quick.widgets.recyclerview.holder.QuickViewHolder
import com.wpf.app.quick.widgets.recyclerview.listeners.DataChangeAdapter
import com.wpf.app.quick.widgets.recyclerview.listeners.DataSelectOnAdapter
import com.wpf.app.quick.widgets.recyclerview.listeners.OnSelectOnChange
import com.wpf.app.quick.widgets.recyclerview.listeners.QuickAdapterListener

/**
 * Created by 王朋飞 on 2022/7/13.
 *
 */
open class QuickAdapter : RecyclerView.Adapter<QuickViewHolder<QuickItemData>>(), DataChangeAdapter {

    var mDataList: MutableList<QuickItemData>? = null

    private var mQuickAdapterListener: QuickAdapterListener<QuickItemData>? = null

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
                    bindingHolder.mViewData = findData
                    bindingHolder.onCreateViewHolder(bindingHolder.itemView)
                    return bindingHolder as QuickViewHolder<QuickItemData>
                }
            }
            val holderAnnotation = findData::class.java.getAnnotation(BindHolder::class.java)
            var holder: QuickViewHolder<out QuickItemData>? = null

            if (holderAnnotation == null) {
                holder = if (findData is QuickBindData) {
                    QuickViewHolder(viewGroup, findData.layoutId)
                } else {
                    throw RuntimeException("当前数据类未使用HolderClass注解指定ViewHolder")
                }
            } else {
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
                if (findData is QuickBindData) {
                    findData.onCreateViewHolder(holder.itemView)
                } else {
                    holder.onCreateViewHolder(holder.itemView)
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
        this.mQuickAdapterListener = listener as QuickAdapterListener<QuickItemData>
    }

    override fun getAdapter(): QuickAdapter {
        return this
    }
}