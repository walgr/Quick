package com.wpf.app.quick.demo.http

import com.wpf.app.quicknetwork.base.*
import com.wpf.app.quicknetwork.helper.RetrofitCreateHelper

inline fun <Data, Fail> request(run: TestApi.() -> WpfRealCall<BaseResponseIA<Data>, BaseResponseIA<Fail>>): BaseRequest<BaseResponseIA<Data>, BaseResponseIA<Fail>> {
    return RetrofitCreateHelper.getService<TestApi>().run(run).enqueue(WpfRequest())
}