package com.wpf.app.quicknetwork.base

import retrofit2.Call

/**
 * Created by 王朋飞 on 2022/7/27.
 *
 */
class WpfCommonCall<S>(rawCall: Call<BaseResponse<S>>, fail: BaseResponse<Any>)
    : WpfNoResponseCall<S, Any>(rawCall = rawCall, fail = fail)