package com.wpf.app.quicknetwork.base

import retrofit2.Call

/**
 * Created by 王朋飞 on 2022/7/27.
 *
 */
open class WpfNoResponseCall<S, F>(rawCall: Call<BaseResponse<S>>, fail: BaseResponse<F>)
    : WpfRealCall<BaseResponse<S>, BaseResponse<F>>(rawCall = rawCall, fail = fail)