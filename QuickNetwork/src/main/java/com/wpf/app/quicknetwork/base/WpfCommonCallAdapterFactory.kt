package com.wpf.app.quicknetwork.base

import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * Created by 王朋飞 on 2022/8/1.
 *
 */
class WpfCommonCallAdapterFactory private constructor(): CallAdapter.Factory() {

    companion object {
        fun create() = WpfCommonCallAdapterFactory()
    }

    override fun get(
        returnType: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {
        val rawType = getRawType(returnType)
        if (rawType == WpfCommonCall::class.java) {
            val sResponseType = getParameterUpperBound(0, returnType as ParameterizedType)
            val sResponseClz = getRawType(sResponseType)
            return WpfCommonCallAdapter(sResponseType, sResponseClz)
        }
        return null
    }
}