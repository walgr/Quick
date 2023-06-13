package com.wpf.app.quickrecyclerview.data

abstract class QuickRequestList(@Transient override val autoSet: Boolean = true) :
    QuickRequestData(autoSet) {

    abstract fun returnList(): List<QuickItemData>?
}