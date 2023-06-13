package com.wpf.app.quickrecyclerview.data

abstract class QuickRequestList(override val autoSet: Boolean = true) : QuickRequestData(autoSet) {

    abstract fun returnList(): List<QuickItemData>?
}