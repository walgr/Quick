package com.wpf.app.quick.base.helper.annotations

import android.view.View

class GroupViews {
    var viewList: MutableList<View> = mutableListOf()

    fun showAll() {
        viewList.forEach { it.visibility = View.VISIBLE }
    }

    fun goneAll() {
        viewList.forEach { it.visibility = View.GONE }
    }
}