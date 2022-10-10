package com.wpf.app.quick.demo.http

import com.wpf.app.quicknetwork.base.BaseResponseIA
import com.wpf.app.quicknetwork.base.BaseResponseIS

class TestResponse<Data>: BaseResponseIA<Data> {
    var data: Data? = null
    var errorCode: String? = null
    var errorMsg: String? = null

    override fun getCodeI(): String? {
        return errorCode
    }

    override fun getErrorI(): String? {
        return errorMsg
    }

    override fun getDataI(): Data? {
        return data
    }

    override fun setDataI(data: Data?) {
        this.data = data
    }
}