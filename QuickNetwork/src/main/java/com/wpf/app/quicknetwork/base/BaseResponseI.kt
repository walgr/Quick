package com.wpf.app.quicknetwork.base

interface BaseResponseIS<Data> : BaseResponseI<Data, String>
interface BaseResponseIA<Data> : BaseResponseI<Data, Any>

interface BaseResponseI<Data, Error> {
    var code: String?
    var error: Error?
    var data: Data?

    fun isSuccess(): Boolean {
        return code == "0"
    }
}