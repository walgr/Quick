package com.wpf.app.base.ability.base

import android.view.View
import androidx.annotation.CallSuper
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelStoreOwner

interface QuickViewAbility: QuickAbility {

    /**
     * 生成页面
     */
    fun generateContentView(owner: LifecycleOwner, view: View): View {
        return view
    }

    fun afterGenerateContentView(owner: ViewModelStoreOwner, view: View) {

    }

    /**
     * 设置页面
     */
    @CallSuper
    fun initView(owner: LifecycleOwner, view: View) {
        initView(view)
    }

    fun initView(view: View) {

    }
}