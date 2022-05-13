package com.wpf.app.base.widgets.recyclerview

import android.util.Log
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import kotlin.reflect.full.findAnnotation

/**
 * Created by 王朋飞 on 2022/5/11.
 *
 */
class CommonAdapter(
    private var dataList: List<CommonItemData>? = null
) : RecyclerView.Adapter<CommonViewHolder<CommonItemData>>() {

    var commonAdapterListener: CommonAdapterListener<out CommonItemData>? = null

    fun setNewData(newData: List<CommonItemData>) {
        this.dataList = newData
        notifyDataSetChanged()
    }

    fun getData(): List<CommonItemData>? {
        return dataList
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CommonViewHolder<CommonItemData> {
        Log.e("创建Holder", "" + viewType)
        dataList!!.find {
            it.viewType == viewType
        }?.let { data ->
            if (data is CommonItemDataBinding<out ViewDataBinding>) {
                val holderAnnotationClass = data::class.findAnnotation<HolderBindingClass>()
                val holderAnnotationLayout = data::class.findAnnotation<HolderBindingLayout>()
                holderAnnotationLayout?.let {
                    val bindingHolder = CommonViewBindingHolder(
                        parent, holderAnnotationLayout.layout, data
                    )
                    bindingHolder.bindViewBinding(bindingHolder.itemView)
                    return bindingHolder as CommonViewHolder<CommonItemData>
                }
                holderAnnotationClass?.let {
                    val bindingHolderCls = it.holderClass.java.getConstructor(ViewGroup::class.java)
                    val bindingHolder =
                        bindingHolderCls.newInstance(parent) as CommonViewBindingHolder<CommonItemDataBinding<out ViewDataBinding>,
                                out ViewDataBinding>
                    bindingHolder.viewData = data
                    bindingHolder.bindViewBinding(bindingHolder.itemView)
                    return bindingHolder as CommonViewHolder<CommonItemData>
                }
            }
            val holderAnnotation = data::class.findAnnotation<HolderClass>()
                ?: throw RuntimeException("当前数据类未使用HolderClass注解指定ViewHolder")
            val holderCls = holderAnnotation.holderClass
            val holderConstructor = holderCls.java.getConstructor(ViewGroup::class.java)
            val holder: CommonViewHolder<CommonItemData> =
                holderConstructor.newInstance(parent) as CommonViewHolder<CommonItemData>
            holder.bindViewBinding(holder.itemView)
            return holder
        }
        return null!!
    }

    override fun onBindViewHolder(holder: CommonViewHolder<CommonItemData>, position: Int) {
        holder.onBindViewHolder(this, dataList!![position], position)
    }

    override fun getItemViewType(position: Int): Int {
        return dataList!![position].viewType
    }

    override fun getItemCount(): Int {
        return dataList?.size ?: 0
    }
}