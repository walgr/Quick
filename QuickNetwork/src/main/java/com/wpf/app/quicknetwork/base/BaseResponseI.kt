package com.wpf.app.quicknetwork.base

interface BaseResponseIS<Data> : BaseResponseI<Data, String>
interface BaseResponseIA<Data> : BaseResponseI<Data, Any>

interface BaseResponseI<Data, Error> {
    var codeI: String?
    var errorI: Error?
    var dataI: Data?

    fun isSuccess(): Boolean {
        return codeI == "0"
    }
}