package com.wpf.app.quick.demo.http.call

import com.wpf.app.quick.demo.http.TestResponse
import com.wpf.app.quicknetwork.call.RealCall
import retrofit2.Call

open class TestCommonCall<SResponse>(rawCall: Call<TestResponse<SResponse>>, fail: TestResponse<Any>) :
    RealCall<TestResponse<SResponse>, TestResponse<Any>>(rawCall, fail)