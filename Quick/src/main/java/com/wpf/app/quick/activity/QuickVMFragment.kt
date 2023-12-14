package com.wpf.app.quick.activity

import android.app.Application
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.lifecycle.ViewModelProvider
import com.wpf.app.quickutil.other.ViewModelEx
import com.wpf.app.quick.activity.viewmodel.QuickViewModel
import com.wpf.app.quickbind.QuickBind
import com.wpf.app.quickbind.interfaces.BindViewModel
import com.wpf.app.quickutil.bind.RunOnContext

/**
 * Created by 王朋飞 on 2022/7/13.
 *
 */
open class QuickVMFragment<VM : QuickViewModel<out QuickView>> @JvmOverloads constructor(
    @LayoutRes layoutId: Int = 0,
    layoutView: View? = null,
    layoutViewInContext: RunOnContext<View>? = null,
    titleName: String = "",
    private val getVMFormActivity: Boolean = false,         //是否获取父Activity的VM
) : QuickFragment(layoutId, layoutView, layoutViewInContext, titleName = titleName),
    BindViewModel<VM> {

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
        initViewModel(this)
    }

    open fun initViewModel(obj: Any) {
        val vmClass: Class<VM>? = ViewModelEx.get0Clazz(obj)
        if (vmClass != null && context != null) {
            mViewModel = ViewModelProvider(
                this,
                ViewModelProvider.AndroidViewModelFactory(requireContext().applicationContext as Application)
            )[vmClass]
            (mViewModel as? QuickViewModel<QuickView>)?.let {
                it.baseView = this
                QuickBind.bind(this, it)
                it.onViewCreated(this)
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (mViewModel == null && getVMFormActivity) {
            initViewModel(requireActivity())
        }
    }

    override fun getViewModel(): VM? {
        return mViewModel
    }

    override fun getTitle(): String? {
        return titleName
    }
}