package com.wpf.app.quick.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.lifecycle.ViewModelProvider
import com.wpf.app.quick.utils.ViewModelEx
import com.wpf.app.quick.viewmodel.QuickViewModel
import com.wpf.app.quickbind.QuickBind.bind
import com.wpf.app.quickbind.interfaces.BindViewModel

/**
 * Created by 王朋飞 on 2022/7/13.
 *
 */
abstract class QuickViewModelActivity<VM : QuickViewModel<H>, H : QuickView> @JvmOverloads constructor(
    @LayoutRes override val layoutId: Int = 0,
    override val layoutView: View? = null,
    override val titleName: String = ""
) : QuickActivity(layoutId, layoutView, titleName),
    BindViewModel<VM> {
    private var mViewModel: VM? = null

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

    override fun dealContentView() {
        super.dealContentView()
        val vmClass: Class<VM>? = ViewModelEx.get0Clazz(this)
        if (vmClass != null) {
            mViewModel = ViewModelProvider(
                this,
                ViewModelProvider.AndroidViewModelFactory(application)
            ).get(vmClass)
            mViewModel?.baseView = this as H
            bind(this, mViewModel)
            mViewModel?.onViewCreated(this as H)
        }
    }

    override fun getViewModel(): VM? {
        return mViewModel
    }

    override fun initView() {

    }
}