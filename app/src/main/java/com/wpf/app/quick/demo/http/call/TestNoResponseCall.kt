package com.wpf.app.quick.demo.http.call

import com.wpf.app.quick.demo.http.TestResponse
import com.wpf.app.quicknetwork.base.BaseResponseS
import com.wpf.app.quicknetwork.call.RealCall
import retrofit2.Call

/**
 * Created by 王朋飞 on 2022/7/27.
 *
 */
open class TestNoResponseCall<SResponse, FResponse>(
    rawCall: Call<TestResponse<SResponse>>,
    fail: TestResponse<FResponse>
) : RealCall<TestResponse<SResponse>, TestResponse<FResponse>>(rawCall, fail)