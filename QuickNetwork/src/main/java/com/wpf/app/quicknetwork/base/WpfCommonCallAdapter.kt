package com.wpf.app.quicknetwork.base

import retrofit2.Call
import retrofit2.CallAdapter
import java.lang.reflect.Type

/**
 * Created by 王朋飞 on 2022/7/22.
 *
 */
class WpfCommonCallAdapter<S>(
    private val responseType: Type,
    sResponse: Class<S>,
) : CallAdapter<BaseResponse<S>, WpfCommonCall<S>> {

    override fun adapt(call: Call<BaseResponse<S>>) = WpfCommonCall(call, BaseResponse())

    override fun responseType() = responseType
}