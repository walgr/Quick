package com.wpf.app.quicknetwork.call

import com.google.gson.Gson
import com.wpf.app.quicknetwork.base.BaseRequest
import com.wpf.app.quicknetwork.base.BaseResponseI
import com.wpf.app.quicknetwork.base.BaseResponseIA
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Response
import java.lang.reflect.Type

/**
 * Created by 王朋飞 on 2022/7/22.
 *
 */
open class RealCall<SResponse, FResponse>(private val rawCall: Call<SResponse>, private val fail: FResponse) {

    /**
     * 异步请求
     */
    fun <Request : BaseRequest<SResponse, FResponse>> enqueue(request: Request): Request {
        CoroutineScope(Dispatchers.Default).launch {
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
                    is Throwable -> {
                        try {
                            if (fail is BaseResponseI<*, *>) {
                                request.funFail.invoke((fail as? BaseResponseI<*, Any>)?.apply {
                                    codeI = "-1"
                                    errorI = result.message
                                } as? FResponse)
                            } else {
                                request.funFail.invoke(fail)
                            }
                        } catch (e: Exception) {
                            e.printStackTrace()
                            request.funFail.invoke(fail)
                        }
                    }
                    is Response<*> -> {
                        var code = ""
                        try {
                            val body = result.body() as SResponse
                            if (request.isSuccess(body)) {
                                request.funSuccess.invoke(body)
                            } else {
                                if (body is BaseResponseI<*, *> && fail is BaseResponseI<*, *>) {
                                    code = body.codeI ?: ""
                                    if (fail.dataI != null) {
                                        body.dataI = Gson().fromJson(
                                            Gson().toJson(body.dataI),
                                            (fail.dataI!!.javaClass as Type)
                                        )
                                    }
                                    request.funFail.invoke((fail as? BaseResponseI<Any, Any>)?.apply {
                                        codeI = body.codeI
                                        errorI = body.errorI
                                        dataI = body.dataI
                                    } as? FResponse)
                                }
                            }
                        } catch (e: Exception) {
                            try {
                                if (fail is BaseResponseI<*, *>) {
                                    request.funFail.invoke((fail as? BaseResponseI<Any, Any>)?.apply {
                                        codeI = code
                                        errorI = e.message
                                    } as? FResponse)
                                } else {
                                    request.funFail.invoke(fail)
                                }
                            } catch (e: Exception) {
                                e.printStackTrace()
                                request.funFail.invoke(fail)
                            }
                        }
                    }
                }
                request.funAfter.invoke()
            }
        }
        return request
    }
}
