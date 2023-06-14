package com.wpf.app.quickrecyclerview.helper

import android.view.View
import com.scwang.smart.refresh.layout.SmartRefreshLayout

fun View.bindRefreshView(autoRefresh: Boolean) {
    //支持SmartRefreshLayout
    if (this.javaClass.name == "com.scwang.smart.refresh.layout.SmartRefreshLayout") {
        (this as? SmartRefreshLayout)?.bindRefreshView(autoRefresh)
    }
}

fun View.afterRequest(isRefresh: Boolean) {
    //支持SmartRefreshLayout
    if (this.javaClass.name == "com.scwang.smart.refresh.layout.SmartRefreshLayout") {
        (this as? SmartRefreshLayout)?.afterRequest(isRefresh)
    }
}

fun View.autoRefresh() {
    //支持SmartRefreshLayout
    if (this.javaClass.name == "com.scwang.smart.refresh.layout.SmartRefreshLayout") {
        (this as? SmartRefreshLayout)?.autoRefresh()
    }
}

fun View.autoRefreshOnlyAnim() {
    //支持SmartRefreshLayout
    if (this.javaClass.name == "com.scwang.smart.refresh.layout.SmartRefreshLayout") {
        (this as? SmartRefreshLayout)?.autoRefreshAnimationOnly()
    }
}