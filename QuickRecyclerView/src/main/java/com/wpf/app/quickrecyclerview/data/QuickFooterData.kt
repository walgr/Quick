package com.wpf.app.quickrecyclerview.data

open class QuickFooterData(
    val isMatch: Boolean = true,             //是否占满
) : QuickViewData() {

    override var dealSpaceItemDecoration: Boolean = false
    override fun dealSpaceItemDecoration(pos: Int): Boolean {
        return false
    }
}