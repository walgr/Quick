package com.wpf.app.quickutil.widget

import androidx.viewpager.widget.ViewPager

fun ViewPager.onPageSelected(pageSelected: (position: Int) -> Unit) {
    addOnPageChangeListener(object : ViewPager.SimpleOnPageChangeListener() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            pageSelected.invoke(position)
        }
    })
}

fun ViewPager.onPageScrollStateChanged(
    onPageScrollStateChanged: (
        state: Int
    ) -> Unit
) {
    addOnPageChangeListener(object : ViewPager.SimpleOnPageChangeListener() {

        override fun onPageScrollStateChanged(state: Int) {
            super.onPageScrollStateChanged(state)
            onPageScrollStateChanged.invoke(state)
        }
    })
}

fun ViewPager.onPageScrolled(
    onPageScrolled: (
        position: Int,
        positionOffset: Float,
        positionOffsetPixels: Int
    ) -> Unit
) {
    addOnPageChangeListener(object : ViewPager.SimpleOnPageChangeListener() {
        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) {
            super.onPageScrolled(position, positionOffset, positionOffsetPixels)
            onPageScrolled.invoke(position, positionOffset, positionOffsetPixels)
        }
    })
}