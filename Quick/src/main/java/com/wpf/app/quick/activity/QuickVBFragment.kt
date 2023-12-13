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
import com.wpf.app.quick.activity.viewmodel.QuickVBModel
import com.wpf.app.quickbind.QuickBind
import com.wpf.app.quickbind.QuickBind.bind

/**
 * Created by 王朋飞 on 2022/7/13.
 *
 */
abstract class QuickVBFragment<VM : QuickVBModel<VB>, VB : ViewDataBinding> @JvmOverloads constructor(
    @LayoutRes layoutId: Int = 0,
    titleName: String = ""
) : QuickFragment(layoutId, titleName = titleName) {

    private var mViewModel: VM? = null
        set(value) {
            field = value
            if (value != null) {
                setViewBinding()
            }
        }

    private var viewBinding: VB? = null

    private fun setViewBinding() {
        viewBinding = DataBindingUtil.bind(requireView())
        viewBinding?.lifecycleOwner = this
        viewBinding?.setVariable(BRConstant.viewModel, mViewModel)
        setBindingVariable(viewBinding)
        viewBinding?.executePendingBindings()
        mViewModel?.mViewBinding = viewBinding
    }

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initViewModel()
        super.onViewCreated(view, savedInstanceState)
        initView(viewBinding)
    }

    open fun initViewModel() {
        val viewModelCls: Class<VM>? = ViewModelEx.get0Clazz(this)
        if (viewModelCls != null && viewModelCls != QuickVBModel::class.java) {
            mViewModel = ViewModelProvider(
                this,
                ViewModelProvider.AndroidViewModelFactory(requireContext().applicationContext as Application)
            )[viewModelCls]
            bind(this, mViewModel)
            mViewModel?.onBindingCreated(viewBinding)
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

    override fun initView(view: View?) {}
    abstract fun initView(viewDataBinding: VB?)

    open fun setBindingVariable(view: VB?) {

    }
}