package com.wpf.app.quickutil.log

import android.view.View
import android.view.ViewGroup
import com.wpf.app.quickutil.other.forceTo
import com.wpf.app.quickutil.other.printLog
import java.util.LinkedList

fun View.printLog() {
    depthFirst(this)
}

private fun depthFirst(root: View) {
    val viewDeque = LinkedList<View>()
    var view = root
    viewDeque.push(view)
    while (!viewDeque.isEmpty()) {
        view = viewDeque.pop()
        printView(view)
        if (view is ViewGroup) {
            for (childIndex in 0 until view.childCount) {
                val childView = view.getChildAt(childIndex)
                viewDeque.push(childView)
            }
        }
    }
}

private fun printView(view : View) {
    view.forceTo<Any>().printLog()
}