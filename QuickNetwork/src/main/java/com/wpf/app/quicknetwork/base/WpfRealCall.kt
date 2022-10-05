package com.wpf.app.quicknetwork.base

import com.google.gson.Gson
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Response
import java.lang.reflect.ParameterizedType

/**
 * Created by 王朋飞 on 2022/7/22.
 *
 */
open class WpfRealCall<S, F>(private val rawCall: Call<S>, private val fail: F) {

    /**
     * 异步请求
     */
    fun <W : BaseRequest<S, F>> enqueue(request: W): W {
        GlobalScope.launch {
            request.funBefore.invoke()
            val result = withContext(Dispatchers.IO) {
                try {
                    rawCall.execute() as Response<S>
                } catch (t: Throwable) {
                    t
                }
            }
            withContext(Dispatchers.Main) {
                when (result) {
                    is Throwable -> request.funFail.invoke(BaseResponse<String>().also {
                        it.errorMessage = BaseResponse.ErrorData(-1, result.message)
                    } as F)
                    is Response<*> -> {
                        var code = 0
                        try {
                            val body = result.body() as S
                            if (request.isSuccess(body)) {
                                request.funSuccess.invoke(body)
                            } else {
                                if (body is BaseResponse<*> && (fail as? ParameterizedType)?.actualTypeArguments?.isNotEmpty() == true) {
                                    code = body.code ?: 0
                                    if (body.data is String) {
                                        body.data = Gson().fromJson(
                                            body.data as String,
                                            (fail as ParameterizedType).actualTypeArguments[0]
                                        )
                                    } else if (body.data is Double || body.data is Float) {
                                        body.data = Gson().fromJson(
                                            body.data.toString(),
                                            (fail as ParameterizedType).actualTypeArguments[0]
                                        )
                                    }
                                }
                                request.funFail.invoke(body as? F)
                            }
                        } catch (e: Exception) {
                            request.funFail.invoke(BaseResponse<String>().also {
                                it.code = code
                                it.errorMessage = BaseResponse.ErrorData(-1, e.message)
                            } as F)
                        }
                    }
                }
                request.funAfter.invoke()
            }
        }
        return request
    }

    /**
     * 同步请求
     */
    fun execute(): S? {
        val response: BaseResponse<S>? = rawCall.execute() as? BaseResponse<S>
        return response?.data
    }
}
