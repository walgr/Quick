package com.wpf.app.quick.demo.http

import com.wpf.app.quicknetwork.base.*
import com.wpf.app.quicknetwork.call.RealCall
import com.wpf.app.quicknetwork.helper.RetrofitCreateHelper

inline fun <Data, Fail> request(run: TestApi.() -> RealCall<Data, Fail>): BaseRequest<Data, Fail> {
    return RetrofitCreateHelper.getService<TestApi>().run(run).enqueue(WpfRequest())
}