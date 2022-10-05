package com.wpf.app.quicknetwork.base

import retrofit2.Call
import retrofit2.CallAdapter
import java.lang.reflect.Type

/**
 * Created by 王朋飞 on 2022/7/22.
 *
 */
class WpfCallAdapter<S, F>(
    private val responseType: Type,
    sResponse: Class<S>,
    private val fResponse: F,
) : CallAdapter<S, WpfRealCall<S, F>> {

    override fun adapt(call: Call<S>) = WpfRealCall(call, fResponse)

    override fun responseType() = responseType
}