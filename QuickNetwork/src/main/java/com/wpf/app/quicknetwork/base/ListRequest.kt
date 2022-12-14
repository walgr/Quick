package com.wpf.app.quicknetwork.base

abstract class ListRequest<SResponse, FResponse> : BaseRequest<SResponse, FResponse>() {
    //是否是刷新
    var isRefresh = true
}