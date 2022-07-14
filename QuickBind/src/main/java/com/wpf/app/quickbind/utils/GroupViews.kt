package com.wpf.app.quickbind.utils

import android.view.View
import java.util.*

/**
 * Created by 王朋飞 on 2022/7/12.
 *
 */
class GroupViews {
    var viewList = ArrayList<View>()

    fun addView(vararg view: View) {
        viewList.addAll(view)
    }

    fun removeView(view: View) {
        viewList.remove(view)
    }

    fun removeAll() {
        viewList.clear()
    }

    fun showAll() {
        for (view in viewList) {
            view.visibility = View.VISIBLE
        }
    }

    fun goneAll() {
        for (view in viewList) {
            view.visibility = View.GONE
        }
    }

    fun onClick(onClickListener: View.OnClickListener?) {
        for (view in viewList) {
            view.setOnClickListener(onClickListener)
        }
    }
}