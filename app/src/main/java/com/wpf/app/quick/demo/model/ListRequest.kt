package com.wpf.app.quick.demo.model

import com.wpf.app.quickrecyclerview.data.RequestData

/**
 * Created by 王朋飞 on 2022/7/8.
 */
class ListRequest(page: Int = 0) : RequestData(page, 40) {

    override fun refresh(): ListRequest {
        return super.refresh() as ListRequest
    }
    override fun loadMore(): ListRequest {
        return super.loadMore() as ListRequest
    }
}