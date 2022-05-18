package com.wpf.app.quick.base.activity

import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.wpf.app.quick.base.constant.BRConstant
import com.wpf.app.quick.base.helper.getVm0Clazz
import com.wpf.app.quick.base.viewmodel.BindingViewModel

/**
 * Created by 王朋飞 on 2022/4/2.
 *  layoutId、layoutView 必须有1个，同时存在时layoutView有效
 *  BRConstant.viewModel的值需要关注官方DataBinding里BR的值 viewModel可能是0 可能是1
 */
abstract class ViewModelBindingActivity<VB : BindingViewModel<out ViewDataBinding>>
@JvmOverloads constructor(
    @LayoutRes private val layoutId: Int? = null,
) : BaseActivity() {

    var viewModel: VB? = null
        set(value) {
            field = value
            setViewBinding()
        }

    private fun setViewBinding() {
        layoutId?.let {
            viewModel?.viewBinding = DataBindingUtil.setContentView(this, layoutId)
            viewModel?.viewBinding?.lifecycleOwner = this
            viewModel?.viewBinding?.setVariable(BRConstant.viewModel, viewModel)
            viewModel?.viewBinding?.executePendingBindings()
        }
    }

    override fun dealContentView() {
        viewModel = ViewModelProvider(this)[getVm0Clazz(this)]
//        lifecycle.addObserver(viewModel!!)
        viewModel?.onModelCreate()
    }

}