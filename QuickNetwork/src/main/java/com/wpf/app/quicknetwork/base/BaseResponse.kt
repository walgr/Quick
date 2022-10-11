package com.wpf.app.quicknetwork.base

/**
 * Created by 王朋飞 on 2022/7/22.
 *
 */
open class BaseResponse<Data>: BaseResponseI<Data, BaseResponse.ErrorData> {
    var code: String? = null
    var errorMessage: ErrorData? = null
    var data: Data? = null

    override var codeI: String? = null
        get() = code
        set(value) {
            field = value
            code = value
        }
    override var errorI: ErrorData? = null
        get() = errorMessage
        set(value) {
            field = value
            errorMessage = value
        }
    override var dataI: Data? = null
        get() = data
        set(value) {
            field = value
            data = value
        }

    //给CallAdapter确定类型使用
    internal constructor()
    internal constructor(data: Class<Data>)
    internal constructor(data: Data?) {
        this.data = data
    }

    class ErrorData(
        val type: Int? = null,
        val message: String? = null,
    )
}

open class BaseResponseS<Data>: BaseResponseIS<Data> {
    var code: String? = null
    var errorMessage: String? = null
    var data: Data? = null

    override var codeI: String? = null
        get() = code
        set(value) {
            field = value
            code = value
        }
    override var errorI: String? = null
        get() = errorMessage
        set(value) {
            field = value
            errorMessage = value
        }
    override var dataI: Data? = null
        get() = data
        set(value) {
            field = value
            data = value
        }

    //给CallAdapter确定类型使用
    internal constructor()
    internal constructor(data: Class<Data>)
    internal constructor(data: Data?) {
        this.data = data
    }
}