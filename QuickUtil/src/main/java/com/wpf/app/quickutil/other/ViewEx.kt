package com.wpf.app.quickutil.other

import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.view.ViewParent
import com.wpf.app.quickutil.data.KVObject

fun <V> ViewGroup.getChild(isViewGroup: (View) -> Boolean): V? {
    for(it in 0 until this.childCount) {
        val child = this.getChildAt(it)
        if (child is ViewGroup) {
            if (isViewGroup.invoke(child)) {
                return child as V
            } else {
                val childCheck = child.getChild<V>(isViewGroup)
                if (childCheck != null) {
                    return childCheck
                }
            }
        } else {
            if (isViewGroup.invoke(child)) {
                return child as V
            }
        }
    }
    return null
}

fun View.removeParent() {
    this.parent.asTo<ViewGroup>()?.removeView(this)
}

fun <T : View>T.addToParent(parent: ViewParent): T {
    parent.asTo<ViewGroup>()?.addView(this)
    return this
}

fun View?.allChild(): List<View> {
    if (this == null) return listOf()
    if (this !is ViewGroup) return listOf()
    val childList = mutableListOf<View>()
    repeat(childCount) {
        val child = getChildAt(it)
        childList.addAll(if (child is ViewGroup) {
            child.allChild()
        } else {
            listOf(child)
        })
    }
    return childList
}

fun View.onceClick(interval: Long = 1000L, onClickListener: OnClickListener) {
    setOnClickListener {
        val viewKey = this.toString()
        val lastClickTime: Long = KVObject.get(viewKey, 0L)
        val curTime = System.currentTimeMillis()
        if (lastClickTime == 0L || curTime - lastClickTime > interval) {
            onClickListener.onClick(it)
            KVObject.put(viewKey, curTime)
        }
    }
}