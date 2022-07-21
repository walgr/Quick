package com.wpf.app.quick.activity

import android.app.Application
import android.os.Bundle
import android.view.View
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.wpf.app.quick.constant.BRConstant
import com.wpf.app.quick.utils.ViewMolderEx
import com.wpf.app.quick.viewmodel.QuickBindingViewModel
import com.wpf.app.quickbind.QuickBind.bind

/**
 * Created by 王朋飞 on 2022/7/13.
 *
 */
abstract class QuickViewModelBindingFragment<VM : QuickBindingViewModel<VB>, VB : ViewDataBinding> @JvmOverloads constructor(
    @LayoutRes override val layoutId: Int = 0,
    override val layoutView: View? = null,
    override val titleName: String = ""
) : QuickFragment(layoutId, layoutView, titleName) {

    protected var mViewModel: VM? = null

    override fun initView(view: View?) {}

    open fun setViewModel(viewModel: VM) {
        mViewModel = viewModel
        setViewBinding()
    }

    private var viewBinding: VB? = null

    open fun getViewBinding(): VB? {
        return viewBinding
    }

    open fun setViewBinding() {
        if (view != null) {
            viewBinding = DataBindingUtil.getBinding(view!!)
            if (viewBinding != null) {
                viewBinding!!.lifecycleOwner = this
                viewBinding!!.setVariable(
                    BRConstant.viewModel,
                    mViewModel
                )
                viewBinding!!.executePendingBindings()
            }
            mViewModel?.mViewBinding = viewBinding
        }
    }

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initViewModel()
        super.onViewCreated(view, savedInstanceState)
        initView(getViewBinding())
    }

    open fun initViewModel() {
        val viewModelCls: Class<VM>? = ViewMolderEx.getVm0Clazz(this)
        if (viewModelCls != null && context != null) {
            setViewModel(
                ViewModelProvider(
                    this,
                    ViewModelProvider.AndroidViewModelFactory(context!!.applicationContext as Application)
                ).get(viewModelCls)
            )
            bind(this, mViewModel)
            if (mViewModel != null) {
                mViewModel!!.onBindingCreated(viewBinding!!)
            }
        } else {
            setViewBinding()
        }
    }

    abstract fun initView(viewDataBinding: VB?)
}