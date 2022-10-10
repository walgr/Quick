package com.wpf.app.quicknetwork.base

import retrofit2.Call
import retrofit2.CallAdapter
import java.lang.reflect.Type

/**
 * Created by 王朋飞 on 2022/7/22.
 *
 */
class WpfCommonCallAdapter<SResponse>(
    private val responseType: Type,
    sResponse: Class<SResponse>,
) : CallAdapter<BaseResponseIA<SResponse>, WpfCommonCall<SResponse>> {

    override fun adapt(call: Call<BaseResponseIA<SResponse>>) = WpfCommonCall(call, object : BaseResponseIA<Any> {
        override fun getCodeI(): String? {
            return ""
        }

        override fun getErrorI(): Any? {
            return ""
        }

        override fun getDataI(): Any? {
            return null
        }

        override fun setDataI(data: Any?) {

        }
    })

    override fun responseType() = responseType
}