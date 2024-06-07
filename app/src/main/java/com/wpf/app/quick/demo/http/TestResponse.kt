package com.wpf.app.quick.demo.http

import com.google.gson.annotations.SerializedName
import com.wpf.app.quick.annotations.request.GenerateCommonCall
import com.wpf.app.quick.annotations.request.GenerateNormalCall
import com.wpf.app.quicknetwork.base.BaseResponseIS

@GenerateCommonCall("TestCommon")
@GenerateNormalCall("TestNormal")
open class TestResponse<Data> @JvmOverloads constructor(
    @SerializedName("data")
    override var data: Data? = null,
    @SerializedName("errorCode")
    override var code: String? = null,
    @SerializedName("errorMsg")
    override var error: String? = null,
): BaseResponseIS<Data>