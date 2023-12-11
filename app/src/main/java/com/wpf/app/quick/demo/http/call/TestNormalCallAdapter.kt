package com.wpf.app.quick.demo.http.call

import com.wpf.app.quick.demo.http.TestResponse
import retrofit2.Call
import retrofit2.CallAdapter
import java.lang.reflect.Type

/**
 * Created by 王朋飞 on 2022/7/22.
 *
 */
class TestNormalCallAdapter<SResponse, FResponse>(
    private val responseType: Type,
    sResponse: Class<SResponse>,
    private val fResponse: TestResponse<FResponse>,
) : CallAdapter<TestResponse<SResponse>, TestNormalCall<SResponse, FResponse>> {

    override fun adapt(call: Call<TestResponse<SResponse>>) = TestNormalCall(call, fResponse)

    override fun responseType() = responseType
}