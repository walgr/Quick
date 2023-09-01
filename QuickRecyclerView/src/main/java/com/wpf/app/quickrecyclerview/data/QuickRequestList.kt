package com.wpf.app.quickrecyclerview.data

/**
 * 可以网络请求列表的数据Item
 */
abstract class QuickRequestList @JvmOverloads constructor(
    @Transient override val autoSet: Boolean = true
) : QuickRequestData() {

    abstract fun returnList(): List<QuickItemData>?
}