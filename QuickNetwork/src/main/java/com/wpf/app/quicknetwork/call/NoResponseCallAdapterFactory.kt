package com.wpf.app.quicknetwork.call

import com.wpf.app.quicknetwork.base.BaseResponseS
import com.wpf.app.quicknetwork.base.BaseResponseIA
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * Created by 王朋飞 on 2022/8/1.
 *
 */
class NoResponseCallAdapterFactory private constructor(): CallAdapter.Factory() {

    companion object {
        fun create() = NoResponseCallAdapterFactory()
    }

    override fun get(
        returnType: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {
        val rawType = getRawType(returnType)
        if (rawType == NoResponseCall::class.java) {
            val sResponseType = getParameterUpperBound(0, returnType as ParameterizedType)
            val fResponseType = getParameterUpperBound(1, returnType)
            val sResponseClz = getRawType(sResponseType)
            val fResponseClz = getRawType(fResponseType)
            val baseResponseF = BaseResponseS(fResponseClz.newInstance())
            return NoResponseCallAdapter(sResponseType, sResponseClz, baseResponseF)
        }
        return null
    }
}