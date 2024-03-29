package com.wpf.app.quicknetwork.call

import com.google.gson.Gson
import com.wpf.app.quicknetwork.base.BaseRequest
import com.wpf.app.quicknetwork.base.BaseResponseI
import com.wpf.app.quicknetwork.base.JobRequest
import com.wpf.app.quickutil.other.asTo
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
        val job = CoroutineScope(Dispatchers.Default).launch {
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
                                request.funFail.invoke(fail.asTo<BaseResponseI<*, Any>>()?.apply {
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
                                if (fail is BaseResponseI<*, *>) {
                                    if (body is BaseResponseI<*, *>) {
                                        code = body.codeI ?: ""
                                        if (fail.dataI != null) {
                                            body.dataI = Gson().fromJson(
                                                Gson().toJson(body.dataI),
                                                (fail.dataI!!.javaClass as Type)
                                            )
                                        }
                                        request.funFail.invoke(fail.asTo<BaseResponseI<Any, Any>>()?.apply {
                                            codeI = body.codeI
                                            errorI = body.errorI
                                            dataI = body.dataI
                                        } as? FResponse)
                                    } else {
                                        request.funFail.invoke(fail.asTo<BaseResponseI<Any, Any>>()?.apply {
                                            codeI = result.code().toString()
                                            errorI = result.message()
                                            dataI = null
                                        } as? FResponse)
                                    }
                                } else {
                                    request.funFail.invoke(fail)
                                }
                            }
                        } catch (e: Exception) {
                            try {
                                if (fail is BaseResponseI<*, *>) {
                                    request.funFail.invoke(fail.asTo<BaseResponseI<Any, Any>>()?.apply {
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
        if (request is JobRequest<*, *>) {
            job.invokeOnCompletion { request.context?.removeJob(job) }
            request.context?.addJob(job)
        }
        return request
    }
}
