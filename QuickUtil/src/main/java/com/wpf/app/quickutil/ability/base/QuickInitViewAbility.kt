package com.wpf.app.quickutil.ability.base

import android.view.View
import androidx.annotation.CallSuper
import com.wpf.app.quickutil.Quick

interface QuickGenerateViewAbility: QuickAbility {
    /**
     * 生成页面
     */
    fun generateContentView(owner: Quick, view: View): View {
        return view
    }


    fun afterGenerateContentView(owner: Quick, view: View) {

    }
}

interface QuickInitViewAbility: QuickGenerateViewAbility {

    /**
     * 设置页面
     */
    @CallSuper
    fun initView(owner: Quick, view: View) {
        initView(view)
    }

    fun initView(view: View) {

    }
}
