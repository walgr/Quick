package com.wpf.app.quick.base.helper

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