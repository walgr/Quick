package com.wpf.app.quicknetwork.call

import com.google.gson.Gson
import com.wpf.app.quicknetwork.base.BaseRequest
import com.wpf.app.quicknetwork.base.BaseResponseI
import com.wpf.app.quicknetwork.base.JobRequest
import com.wpf.app.quickutil.other.asTo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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
    @Suppress("UNCHECKED_CAST")
    fun <Request : BaseRequest<SResponse, FResponse>> enqueue(request: Request): Request {
        val job = CoroutineScope(Dispatchers.Main).launch {
            request.funBefore.invoke()
            val result = withContext(Dispatchers.IO) {
                try {
                    val data = rawCall.execute() as Response<SResponse>
                    val body = data.body() as SResponse
                    if (request.isSuccess(body)) {
                        request.funSuccessBefore.invoke(body)
                    }
                    data
                } catch (t: Throwable) {
                    t
                }
            }
            withContext(Dispatchers.Main) {
                when (result) {
                    is Throwable -> {
                        request.funError.invoke(result)
                    }

                    is Response<*> -> {
                        try {
                            val body = result.body() as SResponse
                            if (request.isSuccess(body)) {
                                request.funSuccess.invoke(body)
                            } else {
                                if (fail is BaseResponseI<*, *>) {
                                    if (body is BaseResponseI<*, *>) {
                                        if (fail.data != null) {
                                            body.data = Gson().fromJson(
                                                Gson().toJson(body.data),
                                                (fail.data!!.javaClass as Type)
                                            )
                                        }
                                        request.funFail.invoke(fail.asTo<BaseResponseI<Any, Any>>()?.apply {
                                            code = body.code
                                            error = body.error
                                            data = body.data
                                        } as? FResponse)
                                    } else {
                                        request.funFail.invoke(fail.asTo<BaseResponseI<Any, Any>>()?.apply {
                                            code = result.code().toString()
                                            error = result.message()
                                            data = null
                                        } as? FResponse)
                                    }
                                } else {
                                    request.funFail.invoke(fail)
                                }
                            }
                        } catch (e: Exception) {
                            request.funError.invoke(e)
                        }
                    }
                }

                request.funFinally.invoke()
            }
        }
        if (request is JobRequest<*, *>) {
            job.invokeOnCompletion { request.context?.removeJob(job) }
            request.context?.addJob(job)
        }
        return request
    }
}
