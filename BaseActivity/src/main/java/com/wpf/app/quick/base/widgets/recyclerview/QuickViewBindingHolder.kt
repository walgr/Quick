package com.wpf.app.quick.base.widgets.recyclerview

import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.wpf.app.quick.base.constant.BRConstant

/**
 * Created by 王朋飞 on 2022/5/12.
 *
 */
open class QuickViewBindingHolder<T : QuickBindingData<H>, H: ViewDataBinding>(
    mParent: ViewGroup,
    @LayoutRes override val layoutId: Int,
    var viewData: T? = null,
    private val variableBinding: Map<Int, Any>? = null,
    override val dealBindView: Boolean = false) :
    QuickViewHolder<T>(mParent, layoutId = layoutId, dealBindView = dealBindView) {

    var viewBinding: H? = null

    override fun onCreateViewHolder(itemView: View) {
        viewBinding = DataBindingUtil.bind(itemView)
        viewData?.onCreateHolderEnd(this)
        onCreateHolderEnd(itemView, viewBinding)
    }

    open fun onCreateHolderEnd(itemView: View, viewDataBinding: ViewDataBinding?) {

    }

    override fun onBindViewHolder(adapter: QuickAdapter, data: T, position: Int) {
        adapterListener = adapter.mQuickAdapterListener as? QuickAdapterListener<T>
        viewBinding?.setVariable(BRConstant.data, data)
        viewBinding?.setVariable(BRConstant.adapter, adapter)
        viewBinding?.setVariable(BRConstant.position, position)
        variableBinding?.forEach {
            viewBinding?.setVariable(it.key, it.value)
        }
        viewBinding?.executePendingBindings()
    }
}