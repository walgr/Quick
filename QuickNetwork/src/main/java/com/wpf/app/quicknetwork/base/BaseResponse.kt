package com.wpf.app.quicknetwork.base

/**
 * Created by 王朋飞 on 2022/7/22.
 *
 */
open class BaseResponse<T> {
    var code: Int? = null
    var errorMessage: ErrorData? = null
    var data: T? = null

    //给CallAdapter确定类型使用
    internal constructor()
    internal constructor(data: Class<T>)
    internal constructor(data: T?) {
        this.data = data
    }

    fun isSuccess(): Boolean {
        return code == 0
    }

    class ErrorData(
        val type: Int? = null,
        val message: String? = null,
    )
}