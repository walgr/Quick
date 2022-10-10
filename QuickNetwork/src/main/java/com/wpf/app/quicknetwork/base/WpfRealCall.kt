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
open class WpfRealCall<SResponse, FResponse>(private val rawCall: Call<SResponse>, private val fail: FResponse) {

    /**
     * 异步请求
     */
    fun <Request : BaseRequest<SResponse, FResponse>> enqueue(request: Request): Request {
        GlobalScope.launch {
            request.funBefore.invoke()
            val result = withContext(Dispatchers.IO) {
                try {
                    rawCall.execute() as Response<SResponse>
                } catch (t: Throwable) {
                    t
                }
            }
            withContext(Dispatchers.Main) {
                when (result) {
                    is Throwable -> request.funFail.invoke(object : BaseResponseIA<String> {
                        override fun getCodeI(): String? {
                            return "-1"
                        }

                        override fun getErrorI(): String? {
                            return result.message
                        }

                        override fun getDataI(): String? {
                            return null
                        }

                        override fun setDataI(data: String?) {
                        }
                    } as FResponse)
                    is Response<*> -> {
                        var code = ""
                        try {
                            val body = result.body() as SResponse
                            if (request.isSuccess(body)) {
                                request.funSuccess.invoke(body)
                            } else {
                                if (body is BaseResponseI<*, *> && (fail as? ParameterizedType)?.actualTypeArguments?.isNotEmpty() == true) {
                                    code = body.getCodeI() ?: ""
                                    if (body.getDataI() is String) {
                                        body.setDataI(Gson().fromJson(
                                            body.getDataI() as String,
                                            (fail as ParameterizedType).actualTypeArguments[0]
                                        ))
                                    } else if (body.getDataI() is Double || body.getDataI() is Float) {
                                        body.setDataI(Gson().fromJson(
                                            body.getDataI().toString(),
                                            (fail as ParameterizedType).actualTypeArguments[0]
                                        ))
                                    }
                                }
                                request.funFail.invoke(body as? FResponse)
                            }
                        } catch (e: Exception) {
                            request.funFail.invoke(object : BaseResponseIA<String> {
                                override fun getCodeI(): String? {
                                    return code
                                }

                                override fun getErrorI(): String? {
                                    return e.message
                                }

                                override fun getDataI(): String? {
                                    return null
                                }

                                override fun setDataI(data: String?) {
                                }
                            } as FResponse)
                        }
                    }
                }
                request.funAfter.invoke()
            }
        }
        return request
    }
}
