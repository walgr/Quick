package com.wpf.app.quicknetwork.call

import com.wpf.app.quicknetwork.base.BaseResponseS
import retrofit2.Call
import retrofit2.CallAdapter
import java.lang.reflect.Type

/**
 * Created by 王朋飞 on 2022/7/22.
 *
 */
class NoResponseCallAdapter<SResponse, FResponse>(
    private val responseType: Type,
    sResponse: Class<SResponse>,
    private val fResponse: BaseResponseS<FResponse>,
) : CallAdapter<BaseResponseS<SResponse>, NoResponseCall<SResponse, FResponse>> {

    override fun adapt(call: Call<BaseResponseS<SResponse>>) = NoResponseCall(call, fResponse)

    override fun responseType() = responseType
}