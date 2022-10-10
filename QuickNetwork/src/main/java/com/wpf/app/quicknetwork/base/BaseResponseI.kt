package com.wpf.app.quicknetwork.base

interface BaseResponseIS<Data> : BaseResponseI<Data, String>
interface BaseResponseIA<Data> : BaseResponseI<Data, Any>

interface BaseResponseI<Data, Error> {
    fun getCodeI(): String?
    fun getErrorI(): Error?
    fun getDataI(): Data?
    fun setDataI(data: Data?)

    fun isSuccess(): Boolean {
        return getCodeI() == "0"
    }
}