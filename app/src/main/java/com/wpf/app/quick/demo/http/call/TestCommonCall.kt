package com.wpf.app.quick.demo.http.call

import com.wpf.app.quick.demo.http.TestResponse
import retrofit2.Call

open class TestCommonCall<SResponse>(rawCall: Call<TestResponse<SResponse>>, fail: TestResponse<Any>) :
    TestNormalCall<SResponse, Any>(rawCall, fail)