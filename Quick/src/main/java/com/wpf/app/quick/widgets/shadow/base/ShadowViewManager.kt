package com.wpf.app.quick.widgets.shadow.base

import com.wpf.app.quickutil.data.KVObject

object ShadowViewManager {

    private const val keyOnAttached = "ShadowViewOnAttached"
    private const val keyOnDetached = "ShadowViewOnDetached"
    fun onAttachedToWindow(view: ShadowView) {
        KVObject.getIfNullPut(keyOnAttached + view.key, mutableListOf<ShadowView>()).add(view)
    }

    fun onDetachedFromWindow(view: ShadowView) {
        KVObject.getIfNullPut(keyOnDetached + view.key, mutableListOf<ShadowView>()).add(view)
        val onAttachedList = KVObject.get<MutableList<ShadowView>>(keyOnAttached + view.key)
        val onDetachedList = KVObject.get<MutableList<ShadowView>>(keyOnDetached + view.key)
        if (onAttachedList != null && onDetachedList != null
            && onAttachedList.size == onDetachedList.size) {
            onDetachedList.forEach {
                KVObject.remove(it.key)
            }
            KVObject.remove(keyOnAttached + view.key)
            KVObject.remove(keyOnDetached + view.key)
        }
    }
}