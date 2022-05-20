package com.wpf.app.quick.base.widgets.recyclerview

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import kotlin.reflect.full.findAnnotation

/**
 * Created by 王朋飞 on 2022/5/11.
 *
 */
class QuickAdapter(
    private var dataList: MutableList<QuickItemData>? = null
) : RecyclerView.Adapter<QuickViewHolder<QuickItemData>>() {

    var mQuickAdapterListener: QuickAdapterListener<out QuickItemData>? = null

    fun setNewData(newData: MutableList<QuickItemData>) {
        this.dataList = newData
        notifyDataSetChanged()
    }

    fun addData(data: QuickItemData) {
        if (this.dataList == null) {
            this.dataList = arrayListOf()
        }
        this.dataList?.add(data)
    }

    fun replaceData(pos: Int, data: QuickItemData) {
        this.dataList?.set(pos, data)
    }

    fun cleanAll() {
        this.dataList?.clear()
        this.dataList = null
        notifyDataSetChanged()
    }

    fun getData(): List<QuickItemData>? {
        return dataList
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): QuickViewHolder<QuickItemData> {
        dataList!!.find {
            it.viewType == viewType
        }?.let { data ->
            if (data is QuickItemDataBinding<out ViewDataBinding>) {
                val holderAnnotationClass = data::class.findAnnotation<HolderBindingClass>()
                val holderAnnotationLayout = data::class.findAnnotation<HolderBindingLayout>()
                holderAnnotationLayout?.let {
                    val bindingHolder = QuickViewBindingHolder(
                        parent, holderAnnotationLayout.layout, data
                    )
                    bindingHolder.onCreateViewHolder(bindingHolder.itemView)
                    return bindingHolder as QuickViewHolder<QuickItemData>
                }
                holderAnnotationClass?.let {
                    val bindingHolderCls = it.holderClass.java.getConstructor(ViewGroup::class.java)
                    val bindingHolder =
                        bindingHolderCls.newInstance(parent) as QuickViewBindingHolder<QuickItemDataBinding<out ViewDataBinding>,
                                out ViewDataBinding>
                    bindingHolder.viewData = data
                    bindingHolder.onCreateViewHolder(bindingHolder.itemView)
                    return bindingHolder as QuickViewHolder<QuickItemData>
                }
            }
            val holderAnnotation = data::class.findAnnotation<HolderClass>()
                ?: throw RuntimeException("当前数据类未使用HolderClass注解指定ViewHolder")
            val holderCls = holderAnnotation.holderClass
            val holderConstructor = holderCls.java.getConstructor(ViewGroup::class.java)
            val holder: QuickViewHolder<QuickItemData> =
                holderConstructor.newInstance(parent) as QuickViewHolder<QuickItemData>
            holder.onCreateViewHolder(holder.itemView)
            return holder
        }
        return null!!
    }

    override fun onBindViewHolder(holder: QuickViewHolder<QuickItemData>, position: Int) {
        holder.onBindViewHolder(this, dataList!![position], position)
    }

    override fun getItemViewType(position: Int): Int {
        return dataList!![position].viewType
    }

    override fun getItemCount(): Int {
        return dataList?.size ?: 0
    }
}