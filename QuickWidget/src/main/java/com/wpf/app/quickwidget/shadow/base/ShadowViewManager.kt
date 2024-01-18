package com.wpf.app.quickwidget.shadow.base

import com.wpf.app.quickutil.data.KV

object ShadowViewManager {

    private const val keyOnAttached = "ShadowViewOnAttached"
    private const val keyOnDetached = "ShadowViewOnDetached"
    fun onAttachedToWindow(view: ShadowView) {
        KV.getIfNullPut(keyOnAttached + view.key, mutableListOf<ShadowView>()).add(view)
    }

    fun onDetachedFromWindow(view: ShadowView) {
        KV.getIfNullPut(keyOnDetached + view.key, mutableListOf<ShadowView>()).add(view)
        val onAttachedList = KV.get<MutableList<ShadowView>>(keyOnAttached + view.key)
        val onDetachedList = KV.get<MutableList<ShadowView>>(keyOnDetached + view.key)
        if (onAttachedList != null && onDetachedList != null
            && onAttachedList.size == onDetachedList.size) {
            onDetachedList.forEach {
                KV.remove(it.key)
            }
            KV.remove(keyOnAttached + view.key)
            KV.remove(keyOnDetached + view.key)
        }
    }
}