package com.wpf.app.quick.activity

import android.view.View
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.wpf.app.quick.constant.BRConstant
import com.wpf.app.quick.utils.ViewMolderEx
import com.wpf.app.quick.viewmodel.QuickBindingViewModel
import com.wpf.app.quickbind.QuickBind

/**
 * Created by 王朋飞 on 2022/7/13.
 *
 */
abstract class QuickViewModelBindingActivity<VM : QuickBindingViewModel<VB>, VB : ViewDataBinding> @JvmOverloads constructor(
    @LayoutRes override val layoutId: Int = 0,
    override val layoutView: View? = null,
    override val titleName: String = ""
) : QuickActivity(layoutId, layoutView, titleName) {

    var mViewModel: VM? = null
        set(value) {
            field = value
            setViewBinding()
        }

    var viewBinding: VB? = null
        private set

    fun setViewBinding() {
        viewBinding = DataBindingUtil.setContentView(this, layoutId)
        viewBinding?.lifecycleOwner = this
        viewBinding?.setVariable(BRConstant.viewModel, mViewModel)
        viewBinding?.executePendingBindings()
        mViewModel?.mViewBinding = viewBinding
    }

    override fun dealContentView() {
        super.dealContentView()
        val viewModelCls: Class<VM>? = ViewMolderEx.getVm0Clazz(this)
        if (viewModelCls != null) {
            mViewModel =
                ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(application)).get(
                    viewModelCls
                )
            QuickBind.bind(this, mViewModel)
            mViewModel?.onBindingCreated(viewBinding)
        } else {
            setViewBinding()
        }
    }

    override fun initView() {
        initView(viewBinding)
    }

    open fun initView(viewDataBinding: VB?) {

    }
}