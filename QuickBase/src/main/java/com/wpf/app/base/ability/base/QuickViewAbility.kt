package com.wpf.app.base.ability.base

import android.view.View
import androidx.lifecycle.ViewModelStoreOwner

interface QuickViewAbility: QuickAbility {

    /**
     * 生成页面
     */
    fun generateContentView(owner: ViewModelStoreOwner, view: View): View {
        return view
    }

    fun afterGenerateContentView(owner: ViewModelStoreOwner, view: View) {

    }

    /**
     * 设置页面
     */
    fun initView(view: View) {

    }
}
