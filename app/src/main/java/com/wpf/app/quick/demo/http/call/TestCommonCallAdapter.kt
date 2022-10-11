package com.wpf.app.quick.demo.http.call

import com.wpf.app.quick.demo.http.TestResponse
import com.wpf.app.quicknetwork.base.BaseResponseIA
import retrofit2.Call
import retrofit2.CallAdapter
import java.lang.reflect.Type

/**
 * Created by 王朋飞 on 2022/7/22.
 *
 */
class TestCommonCallAdapter<SResponse>(
    private val responseType: Type,
    sResponse: Class<SResponse>,
) : CallAdapter<TestResponse<SResponse>, TestCommonCall<SResponse>> {

    override fun adapt(call: Call<TestResponse<SResponse>>) = TestCommonCall(call, TestResponse())

    override fun responseType() = responseType
}