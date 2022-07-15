package com.wpf.app.quick.widgets.recyclerview

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by 王朋飞 on 2022/7/13.
 *
 */
class QuickAdapter : RecyclerView.Adapter<QuickViewHolder<QuickItemData>>() {

    private var mDataList: MutableList<QuickItemData>? = null

    var mQuickAdapterListener: QuickAdapterListener<out QuickItemData>? = null

    fun setNewData(mDataList: List<QuickItemData>?) {
        cleanAll()
        appendList(mDataList)
        notifyDataSetChanged()
    }

    fun cleanAll() {
        if (mDataList != null) {
            mDataList!!.clear()
        }
        notifyDataSetChanged()
    }

    fun appendList(mDataList: List<QuickItemData>?) {
        if (mDataList == null) return
        if (this.mDataList == null) {
            this.mDataList = mutableListOf()
        }
        this.mDataList!!.addAll(mDataList)
    }

    fun addData(data: QuickItemData) {
        if (mDataList == null) {
            mDataList = ArrayList()
        }
        mDataList!!.add(data)
    }

    fun addData(
        pos: Int,
        data: QuickItemData
    ) {
        if (mDataList == null) {
            mDataList = mutableListOf()
        }
        mDataList?.add(pos, data)
    }

    fun getData(): MutableList<QuickItemData>? {
        return mDataList
    }

    fun <T : QuickItemData> getRealTypeData(): MutableList<T>? {
        return mDataList as? MutableList<T>
    }

    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int
    ): QuickViewHolder<QuickItemData> {
        mDataList?.find {
            it.viewType == viewType
        }?.let { findData ->
            if (findData is QuickViewDataBinding<*>) {
                val holderAnnotationClass = findData::class.java.getAnnotation(BindBindingHolder::class.java)
                holderAnnotationClass?.let {
                    val bindingHolderCls = it.value.java.getConstructor(ViewGroup::class.java)
                    val bindingHolder =
                        bindingHolderCls.newInstance(viewGroup) as QuickViewBindingHolder<QuickViewDataBinding<out ViewDataBinding>, out ViewDataBinding>
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
            holder?.onCreateViewHolder(holder.itemView)
            //            if (findData instanceof QuickBindData) {
//                ((QuickBindData) findData).onCreateViewHolder((QuickViewHolder<? extends QuickBindData>)holder);
//            }
            return holder as QuickViewHolder<QuickItemData>
        }
        return null!!
    }

    override fun onBindViewHolder(viewHolder: QuickViewHolder<QuickItemData>, position: Int) {
        viewHolder.onBindViewHolder(this, mDataList!![position], position)
    }

    override fun getItemCount(): Int {
        return mDataList?.size ?: 0
    }

    fun getQuickAdapterListener(): QuickAdapterListener<out QuickItemData>? {
        return mQuickAdapterListener
    }
}