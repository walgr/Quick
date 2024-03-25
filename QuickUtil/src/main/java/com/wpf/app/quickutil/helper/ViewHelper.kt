package com.wpf.app.quickutil.helper

import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import android.view.ViewParent
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.wpf.app.quickutil.data.KV
import com.wpf.app.quickutil.other.asTo
import com.wpf.app.quickutil.other.forceTo

fun <V: View> ViewGroup.getChild(isViewGroup: (View) -> Boolean): V? {
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

fun View.onceClick(interval: Long = 1000L, onClickListener: (View) -> Unit) {
    setOnClickListener {
        val viewKey = it.toString()
        val lastClickTime: Long = KV.get(viewKey, 0L)
        val curTime = System.currentTimeMillis()
        if (lastClickTime == 0L || curTime - lastClickTime > interval) {
            onClickListener.invoke(it)
            KV.put(viewKey, curTime)
        }
    }
}

fun View?.getLocationInWindow(): IntArray {
    val location = intArrayOf(0, 0)
    if (this == null) return location
    getLocationInWindow(location)
    return location
}


/**
 * Created by 王朋飞 on 2022/7/27.
 * 请在onAttachedToWindow中执行
 * 返回view所在的Fragment或Activity
 */
fun View.getViewContext(): Any? {
    var viewParent: ViewParent? = parent ?: return null
    while (viewParent != null) {
        if (viewParent is ViewPager) {
            val fragments = (viewParent.context as AppCompatActivity).supportFragmentManager.fragments
            fragments.forEach {
                if (it.view?.findViewById<View>(id) == this) {
                    return it
                }
            }
        }
        viewParent = viewParent.parent
        if (viewParent == null) break
    }
    return null
}

fun View.setMarginEnd(margin: Int) {
    layoutParams.forceTo<MarginLayoutParams>().marginEnd = margin
}

fun View.setMarginStart(margin: Int) {
    layoutParams.forceTo<MarginLayoutParams>().marginStart = margin
}

fun View.setTopMargin(margin: Int) {
    layoutParams.forceTo<MarginLayoutParams>().topMargin = margin
}

fun View.seBottomMargin(margin: Int) {
    layoutParams.forceTo<MarginLayoutParams>().bottomMargin = margin
}

fun View.postDelay(delayMillis: Long, action: Runnable) {
    postDelayed(action, delayMillis)
}

fun View.parent() = parent as? ViewGroup