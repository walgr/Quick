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
open class CommonViewBindingHolder<T : CommonItemDataBinding<H>, H: ViewDataBinding>(
    mParent: ViewGroup,
    @LayoutRes override val layoutId: Int = 0,
    var viewData: T? = null,
    private val variableBinding: Map<Int, Any>? = null) :
    CommonViewHolder<T>(mParent, layoutId = layoutId) {

    var viewBinding: H? = null

    override fun bindViewBinding(view: View) {
        viewBinding = DataBindingUtil.bind(view)
//        viewBinding = viewData?.viewBinding
//        val viewDataBinding: ViewDataBinding = getVm0Clazz(viewData!!)
//        viewBinding?.setVariable(BRConstant.data, viewData)
//        viewBinding?.executePendingBindings()
        viewData?.onViewBindingSet(this)
    }

    override fun onBindViewHolder(adapter: CommonAdapter, data: T, position: Int) {
        viewBinding?.setVariable(BRConstant.data, data)
        viewBinding?.setVariable(BRConstant.adapter, adapter)
        viewBinding?.setVariable(BRConstant.position, position)
        variableBinding?.forEach {
            viewBinding?.setVariable(it.key, it.value)
        }
        viewBinding?.executePendingBindings()
    }
}