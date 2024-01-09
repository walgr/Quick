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
import com.wpf.app.quickutil.other.ViewModelEx
import com.wpf.app.quick.activity.viewmodel.QuickVBModel
import com.wpf.app.quickbind.QuickBind.bind
import com.wpf.app.quickutil.bind.RunOnContext

/**
 * Created by 王朋飞 on 2022/7/13.
 *
 */
abstract class QuickVBFragment<VM : QuickVBModel<VB>, VB : ViewDataBinding> @JvmOverloads constructor(
    @LayoutRes layoutId: Int = 0,
    layoutView: View? = null,
    layoutViewInContext: RunOnContext<View>? = null,
    titleName: String = "",
    private val getVMFormActivity: Boolean = false,         //是否获取父Activity的VM, VB
) : QuickFragment(layoutId, layoutView, layoutViewInContext, titleName = titleName) {

    private var mViewModel: VM? = null
        set(value) {
            field = value
            if (value != null) {
                bind(this, value)
                setViewBinding()
                value.onBindingCreated(viewBinding)
            }
        }

    private var viewBinding: VB? = null

    private fun setViewBinding() {
        if (viewBinding == null) {
            viewBinding = DataBindingUtil.bind(requireView())
            viewBinding?.lifecycleOwner = this
        }
        viewBinding?.setVariable(BRConstant.viewModel, mViewModel)
        setBindingVariable(viewBinding)
        viewBinding?.executePendingBindings()
        mViewModel?.mViewBinding = viewBinding
    }

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initViewModel(this)
        super.onViewCreated(view, savedInstanceState)
        initView(viewBinding)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (mViewModel == null && getVMFormActivity) {
            initViewModel(requireActivity())
            initView(viewBinding)
        }
    }

    open fun initViewModel(obj: Any) {
        val viewModelCls: Class<VM>? = ViewModelEx.get0Clazz(obj)
        if (viewModelCls != null && this !is QuickBindingFragment && viewModelCls != QuickVBModel::class.java) {
            mViewModel = ViewModelProvider(
                this,
                ViewModelProvider.AndroidViewModelFactory(requireContext().applicationContext as Application)
            )[viewModelCls]
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

    @Deprecated("Deprecated by Fragment")
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
    open fun initView(view: VB?) {

    }

    open fun setBindingVariable(view: VB?) {

    }
}