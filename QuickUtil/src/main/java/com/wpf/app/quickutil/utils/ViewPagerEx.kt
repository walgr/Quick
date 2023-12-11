package com.wpf.app.quickutil.utils

import androidx.viewpager.widget.ViewPager

fun ViewPager.onPageSelected(pageSelected: (position: Int) -> Unit) {
    addOnPageChangeListener(object : ViewPager.SimpleOnPageChangeListener() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            pageSelected.invoke(position)
        }
    })
}