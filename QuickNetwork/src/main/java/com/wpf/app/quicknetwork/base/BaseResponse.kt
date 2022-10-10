package com.wpf.app.quicknetwork.base

/**
 * Created by 王朋飞 on 2022/7/22.
 *
 */
open class BaseResponse<Data>: BaseResponseI<Data, BaseResponse.ErrorData> {
    var code: String? = null
    var errorMessage: ErrorData? = null
    var data: Data? = null

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

    override fun getCodeI(): String? {
        return code
    }

    override fun getErrorI(): ErrorData? {
        return errorMessage
    }

    override fun setDataI(data: Data?) {
        this.data = data
    }

    override fun getDataI(): Data? {
        return data
    }
}

open class BaseResponseA<Data>: BaseResponseIA<Data> {
    var code: String? = null
    var errorMessage: Any? = null
    var data: Data? = null

    //给CallAdapter确定类型使用
    internal constructor()
    internal constructor(data: Class<Data>)
    internal constructor(data: Data?) {
        this.data = data
    }

    override fun getCodeI(): String? {
        return code
    }

    override fun getErrorI(): Any? {
        return errorMessage
    }

    override fun setDataI(data: Data?) {
        this.data = data
    }

    override fun getDataI(): Data? {
        return data
    }
}