package com.wpf.app.quick.demo.http

import com.wpf.app.quick.annotations.request.GenerateCommonCall
import com.wpf.app.quick.annotations.request.GenerateNormalCall
import com.wpf.app.quicknetwork.base.BaseResponseIS

@GenerateCommonCall("TestCommon")
@GenerateNormalCall("TestNormal")
open class TestResponse<Data> @JvmOverloads constructor(
    var data: Data? = null,
    var errorCode: String? = null,
    var errorMsg: String? = null,
): BaseResponseIS<Data> {
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
}