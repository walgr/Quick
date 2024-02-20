package com.wpf.app.quick.demo.wanandroid.model

import com.wpf.app.quick.annotations.getclass.GetClass
import com.wpf.app.quickrecyclerview.data.RequestData

/**
 * Created by 王朋飞 on 2022/7/8.
 */
@GetClass
class WanRequest(page: Int = 0) : RequestData(page, 20) {

    override fun refresh(): WanRequest {
        return super.refresh() as WanRequest
    }
    override fun loadMore(): WanRequest {
        return super.loadMore() as WanRequest
    }
}