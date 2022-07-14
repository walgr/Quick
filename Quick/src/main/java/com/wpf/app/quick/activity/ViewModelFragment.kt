package com.wpf.app.quick.activity

import android.app.Application
import android.view.View
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.lifecycle.ViewModelProvider
import com.wpf.app.quick.utils.ViewMolderEx
import com.wpf.app.quick.viewmodel.BaseViewModel
import com.wpf.app.quickbind.QuickBind.bind
import com.wpf.app.quickbind.annotations.AutoGet

/**
 * Created by 王朋飞 on 2022/7/13.
 *
 */
open class ViewModelFragment<VM : BaseViewModel<H>, H : QuickView>(
    @LayoutRes override val layoutId: Int = 0,
    override val layoutView: View? = null,
    override val titleName: String = ""
) : QuickFragment() {

    private var mViewModel: VM? = null

    @CallSuper
    override fun initView(view: View?) {
        initViewModel()
    }

    open fun initViewModel() {
        val vmClass: Class<VM>? = ViewMolderEx.getVm0Clazz(this)
        if (vmClass != null && context != null) {
            mViewModel = ViewModelProvider(
                this,
                ViewModelProvider.AndroidViewModelFactory(context!!.applicationContext as Application)
            )
                .get(vmClass)
            bind(this, mViewModel)
            mViewModel?.baseView = this as H
            mViewModel?.onViewCreated(this as H)
        }
    }

    open fun getViewModel(): VM? {
        return mViewModel
    }

    override fun getTitle(): String? {
        return titleName
    }
}