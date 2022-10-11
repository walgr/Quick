package com.wpf.app.quick.demo.http

import com.wpf.app.quicknetwork.base.BaseResponseIS

open class TestResponse<Data>: BaseResponseIS<Data> {
    var data: Data? = null
    var errorCode: String? = null
    var errorMsg: String? = null
    override var codeI: String? = null
        get() = errorCode
        set(value) {
            field = value
            errorCode = value
        }
    override var errorI: String? = null
        get() = errorMsg
        set(value) {
            field = value
            errorMsg = value
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