package com.wpf.app.quick.base.activity

import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.wpf.app.quick.base.constant.BRConstant
import com.wpf.app.quick.base.helper.QuickBindHelper
import com.wpf.app.quick.base.helper.getVm0Clazz
import com.wpf.app.quick.base.viewmodel.BindingViewModel

/**
 * Created by 王朋飞 on 2022/4/2.
 *  layoutId、layoutView 必须有1个，同时存在时layoutView有效
 *  BRConstant.viewModel的值需要关注官方DataBinding里BR的值 viewModel可能是0 可能是1
 */
abstract class ViewModelBindingActivity<VM : BindingViewModel<VB>, VB : ViewDataBinding> constructor(
    @LayoutRes override val layoutId: Int,
    override val activityTitle: String = "",
) : BaseActivity(activityTitle = activityTitle) {

    var viewModel: VM? = null
        set(value) {
            field = value
            setViewBinding()
        }

    private var viewBinding: VB? = null
    fun getViewBinding(): VB? {
        return viewBinding
    }

    private fun setViewBinding() {
        viewBinding = DataBindingUtil.setContentView(this, layoutId)
        viewBinding?.lifecycleOwner = this
        viewBinding?.setVariable(BRConstant.viewModel, viewModel)
        viewBinding?.executePendingBindings()
        viewModel?.viewBinding = viewBinding
    }

    override fun dealContentView() {
        val viewModelCls = getVm0Clazz<Class<VM>>(this)
        if (viewModelCls != null) {
            viewModel = ViewModelProvider(this)[viewModelCls]
            QuickBindHelper.bind(this, viewModel)
            viewModel?.onBindingCreate(viewModel?.viewBinding)
        } else {
            setViewBinding()
        }
    }

    override fun initView() {
        super.initView()
        initView(viewBinding)
    }

    open fun initView(viewDataBinding: VB?) {

    }
}