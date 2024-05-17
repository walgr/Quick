package com.wpf.app.base.ability.base

import android.view.View
import androidx.annotation.CallSuper
import com.wpf.app.base.QuickView

interface QuickViewAbility: QuickAbility {

    /**
     * 生成页面
     */
    fun generateContentView(owner: QuickView, view: View): View {
        return view
    }


    fun afterGenerateContentView(owner: QuickView, view: View) {

    }

    /**
     * 设置页面
     */
    @CallSuper
    fun initView(owner: QuickView, view: View) {
        initView(view)
    }

    fun initView(view: View) {

    }
}
