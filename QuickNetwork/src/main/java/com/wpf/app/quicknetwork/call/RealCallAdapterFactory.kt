package com.wpf.app.quicknetwork.call

import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * Created by 王朋飞 on 2022/7/22.
 *
 */
class RealCallAdapterFactory private constructor(): CallAdapter.Factory() {

    companion object {
        fun create() = RealCallAdapterFactory()
    }

    override fun get(
        returnType: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {
        val rawType = getRawType(returnType)
        if (rawType == RealCall::class.java) {
            val sResponseType = getParameterUpperBound(0, returnType as ParameterizedType)
            val fResponseType = getParameterUpperBound(1, returnType)
            val sResponseClz = getRawType(sResponseType)
            return RealCallAdapter(sResponseType, sResponseClz, fResponseType)
        }
        return null
    }
}