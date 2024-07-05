package com.wpf.app.quickutil.ability.helper

import android.content.Context
import android.view.View
import com.wpf.app.quickutil.ability.scope.ContextScope
import com.wpf.app.quickutil.ability.scope.createContextScope

fun viewCreateConvert(layoutViewCreate: (ContextScope.() -> View)?) : (Context.() -> View)? {
    return layoutViewCreate?.let {
        object : (Context) -> View {
            override fun invoke(p1: Context): View {
                return it.invoke(p1.createContextScope())
            }
        }
    }
}