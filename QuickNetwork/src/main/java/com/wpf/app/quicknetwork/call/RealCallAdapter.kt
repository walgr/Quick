package com.wpf.app.quicknetwork.call

import retrofit2.Call
import retrofit2.CallAdapter
import java.lang.reflect.Type

/**
 * Created by 王朋飞 on 2022/7/22.
 *
 */
@Deprecated("已废弃", level = DeprecationLevel.WARNING)
class RealCallAdapter<S, F>(
    private val responseType: Type,
    sResponse: Class<S>,
    private val fResponse: F,
) : CallAdapter<S, RealCall<S, F>> {

    override fun adapt(call: Call<S>) = RealCall(call, fResponse)

    override fun responseType() = responseType
}