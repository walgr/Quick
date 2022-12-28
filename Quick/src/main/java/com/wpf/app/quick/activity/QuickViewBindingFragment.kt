package com.wpf.app.quick.activity

import android.app.Application
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.wpf.app.quickrecyclerview.constant.BRConstant
import com.wpf.app.quickutil.base.ViewModelEx
import com.wpf.app.quick.activity.viewmodel.QuickBindingModel
import com.wpf.app.quickbind.QuickBind.bind

/**
 * Created by 王朋飞 on 2022/7/13.
 *
 */
abstract class QuickViewBindingFragment<VM : QuickBindingModel<VB>, VB : ViewDataBinding> @JvmOverloads constructor(
    @LayoutRes override val layoutId: Int = 0,
    override val titleName: String = ""
) : QuickFragment(layoutId, titleName = titleName) {

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
            viewBinding = DataBindingUtil.getBinding(requireView())
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
        val viewModelCls: Class<VM>? = ViewModelEx.get0Clazz(this)
        if (viewModelCls != null && context != null) {
            setViewModel(
                ViewModelProvider(
                    this,
                    ViewModelProvider.AndroidViewModelFactory(requireContext().applicationContext as Application)
                )[viewModelCls]
            )
            bind(this, mViewModel)
            if (mViewModel != null) {
                mViewModel!!.onBindingCreated(viewBinding!!)
            }
        } else {
            setViewBinding()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mViewModel?.onSaveInstanceState(outState)
    }

    override fun onResume() {
        super.onResume()
        mViewModel?.onResume()
    }

    override fun onPause() {
        super.onPause()
        mViewModel?.onPause()
    }

    override fun onStop() {
        super.onStop()
        mViewModel?.onStop()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        mViewModel?.onActivityResult(requestCode, resultCode, data)
    }

    override fun onDestroy() {
        super.onDestroy()
        mViewModel?.onDestroy()
        mViewModel = null
    }

    abstract fun initView(viewDataBinding: VB?)
}