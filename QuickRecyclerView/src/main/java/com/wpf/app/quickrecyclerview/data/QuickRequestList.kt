package com.wpf.app.quickrecyclerview.data

/**
 * 可以网络请求列表的数据Item
 */
abstract class QuickRequestList @JvmOverloads constructor(
    autoSet: Boolean = true
) : QuickRequestData(autoSet = autoSet) {

    abstract fun returnList(): List<QuickItemData>?
}