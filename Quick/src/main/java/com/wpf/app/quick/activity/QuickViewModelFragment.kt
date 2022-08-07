package com.wpf.app.quick.activity

import android.app.Application
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.lifecycle.ViewModelProvider
import com.wpf.app.quick.utils.ViewMolderEx
import com.wpf.app.quick.viewmodel.QuickViewModel
import com.wpf.app.quickbind.QuickBind
import com.wpf.app.quickbind.interfaces.BindViewModel

/**
 * Created by 王朋飞 on 2022/7/13.
 *
 */
open class QuickViewModelFragment<VM : QuickViewModel<H>, H : QuickView> @JvmOverloads constructor(
    @LayoutRes override val layoutId: Int = 0,
    override val layoutView: View? = null,
    override val titleName: String = ""
) : QuickFragment(), BindViewModel<VM> {

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
            ).get(vmClass)
            QuickBind.bind(this, mViewModel)
            mViewModel?.baseView = this as H
            mViewModel?.onViewCreated(this as H)
        }
    }

    override fun getViewModel(): VM? {
        return mViewModel
    }

    override fun getTitle(): String? {
        return titleName
    }
}