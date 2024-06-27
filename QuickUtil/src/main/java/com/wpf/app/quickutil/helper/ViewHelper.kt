package com.wpf.app.quickutil.helper

import android.graphics.drawable.Drawable
import android.view.View
import android.view.View.OnClickListener
import android.view.View.OnLongClickListener
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import android.view.ViewParent
import android.widget.LinearLayout
import android.widget.LinearLayout.LayoutParams
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.setPadding
import androidx.viewpager.widget.ViewPager
import com.wpf.app.quickutil.data.KV
import com.wpf.app.quickutil.other.asTo
import com.wpf.app.quickutil.other.forceTo

fun <V : View> ViewGroup.getChild(isViewGroup: (View) -> Boolean): V? {
    for (it in 0 until this.childCount) {
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

fun View.removeParent(): View {
    this.parent.asTo<ViewGroup>()?.removeView(this)
    return this
}

fun <T : View> T.addToParent(parent: ViewParent): T {
    parent.asTo<ViewGroup>()?.addView(this)
    return this
}

fun ViewGroup.children(): List<View> {
    val childList = mutableListOf<View>()
    repeat(childCount) {
        val child = getChildAt(it)
        childList.add(child)
    }
    return childList
}

fun <T : View> T.onceClick(interval: Long = 1000L, onClickListener: OnClickListener): T {
    onceClick(interval) {
        onClickListener.onClick(it)
    }
    return this
}

fun <T : View> T.onceClick(interval: Long = 1000L, onClickListener: (View) -> Unit): T {
    setOnClickListener {
        val viewKey = it.toString()
        val lastClickTime: Long = KV.get(viewKey, 0L)
        val curTime = System.currentTimeMillis()
        if (lastClickTime == 0L || curTime - lastClickTime > interval) {
            onClickListener.invoke(it)
            KV.put(viewKey, curTime)
        }
    }
    return this
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
            val fragments =
                (viewParent.context as AppCompatActivity).supportFragmentManager.fragments
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

fun <T : View> T.margin(margin: Int): T {
    layoutParams.forceTo<MarginLayoutParams>().marginStart = margin
    layoutParams.forceTo<MarginLayoutParams>().topMargin = margin
    layoutParams.forceTo<MarginLayoutParams>().marginEnd = margin
    layoutParams.forceTo<MarginLayoutParams>().bottomMargin = margin
    return this
}

fun <T : View> T.marginVertical(margin: Int): T {
    layoutParams.forceTo<MarginLayoutParams>().topMargin = margin
    layoutParams.forceTo<MarginLayoutParams>().bottomMargin = margin
    return this
}

fun <T : View> T.marginHorizontal(margin: Int): T {
    layoutParams.forceTo<MarginLayoutParams>().marginStart = margin
    layoutParams.forceTo<MarginLayoutParams>().marginEnd = margin
    return this
}

fun <T : View> T.marginEnd(margin: Int): T {
    layoutParams.forceTo<MarginLayoutParams>().marginEnd = margin
    return this
}

fun <T : View> T.marginStart(margin: Int): T {
    layoutParams.forceTo<MarginLayoutParams>().marginStart = margin
    return this
}

fun <T : View> T.marginTop(margin: Int): T {
    layoutParams.forceTo<MarginLayoutParams>().topMargin = margin
    return this
}

fun <T : View> T.marginBottom(margin: Int): T {
    layoutParams.forceTo<MarginLayoutParams>().bottomMargin = margin
    return this
}

fun <T : View> T.weight(weight: Float): T {
    parent()?.forceTo<LinearLayout>()?.let {
        if (it.orientation == LinearLayout.VERTICAL) {
            this@weight.layoutParams.height = 0
        } else {
            this@weight.layoutParams.width = 0
        }
        this@weight.layoutParams.forceTo<LayoutParams>().weight = weight
    }
    return this
}

fun <T : View> T.padding(size: Int): T {
    this.setPadding(size)
    return this
}

fun <T : View> T.padding(
    start: Int = this.paddingLeft,
    top: Int = this.paddingTop,
    end: Int = this.paddingRight,
    bottom: Int = this.paddingBottom,
): T {
    this.setPadding(start, top, end, bottom)
    return this
}

fun <T : View> T.paddingVertical(size: Int): T {
    padding(top = size, bottom = size)
    return this
}

fun <T : View> T.paddingHorizontal(size: Int): T {
    padding(start = size, end = size)
    return this
}

fun <T : TextView> T.drawableStart(drawable: Drawable, padding: Int = 0): T {
    drawable.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
    setCompoundDrawables(drawable, null, null, null)
    compoundDrawablePadding = padding
    return this
}

fun <T : TextView> T.drawableEnd(drawable: Drawable, padding: Int = 0): T {
    drawable.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
    setCompoundDrawables(null, null, drawable, null)
    compoundDrawablePadding = padding
    return this
}

fun <T : TextView> T.drawableTop(drawable: Drawable, padding: Int = 0): T {
    drawable.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
    setCompoundDrawables(null, drawable, null, null)
    compoundDrawablePadding = padding
    return this
}

fun <T : TextView> T.drawableBottom(drawable: Drawable, padding: Int = 0): T {
    drawable.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
    setCompoundDrawables(null, null, null, drawable)
    compoundDrawablePadding = padding
    return this
}

fun <T : TextView> T.drawable(start: Drawable, top: Drawable, end: Drawable, bottom: Drawable, padding: Int = 0): T {
    start.setBounds(0, 0, start.intrinsicWidth, start.intrinsicHeight)
    top.setBounds(0, 0, top.intrinsicWidth, top.intrinsicHeight)
    end.setBounds(0, 0, end.intrinsicWidth, end.intrinsicHeight)
    bottom.setBounds(0, 0, bottom.intrinsicWidth, bottom.intrinsicHeight)
    setCompoundDrawables(start, top, end, bottom)
    compoundDrawablePadding = padding
    return this
}

fun View.postDelay(delayMillis: Long, action: Runnable) {
    postDelayed(action, delayMillis)
}

fun View.parent() = parent as? ViewGroup

fun View.onClick(clickListener: OnClickListener): View {
    setOnClickListener(clickListener)
    return this
}

fun View.onLongClick(longClickListener: OnLongClickListener): View {
    setOnLongClickListener(longClickListener)
    return this
}