package com.wpf.app.quick.activity

import android.content.Intent
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.wpf.app.quickrecyclerview.constant.BRConstant
import com.wpf.app.quickutil.base.ViewModelEx
import com.wpf.app.quick.activity.viewmodel.QuickVBModel
import com.wpf.app.quickbind.QuickBind

/**
 * Created by 王朋飞 on 2022/7/13.
 *
 */
abstract class QuickVBActivity<VM : QuickVBModel<VB>, VB : ViewDataBinding> @JvmOverloads constructor(
    @LayoutRes override val layoutId: Int = 0,
    override val titleName: String = ""
) : QuickActivity(layoutId, titleName = titleName) {

    var mViewModel: VM? = null
        set(value) {
            field = value
            if (value != null) {
                setViewBinding()
            }
        }

    var viewBinding: VB? = null
        private set

    private fun setViewBinding() {
        viewBinding = DataBindingUtil.setContentView(this, layoutId)
        viewBinding?.lifecycleOwner = this
        viewBinding?.setVariable(BRConstant.viewModel, mViewModel)
        setBindingVariable(viewBinding)
        viewBinding?.executePendingBindings()
        mViewModel?.mViewBinding = viewBinding
    }

    override fun dealContentView() {
        super.dealContentView()
        val viewModelCls: Class<VM>? = ViewModelEx.get0Clazz(this)
        if (viewModelCls != null && this !is QuickBindingActivity && viewModelCls != QuickVBModel::class.java) {
            mViewModel = ViewModelProvider(
                this,
                ViewModelProvider.AndroidViewModelFactory(application)
            )[viewModelCls]
            QuickBind.bind(this, mViewModel)
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

    override fun initView() {
        initView(viewBinding)
    }

    open fun initView(viewDataBinding: VB?) {

    }

    open fun setBindingVariable(viewDataBinding: VB?) {

    }
}