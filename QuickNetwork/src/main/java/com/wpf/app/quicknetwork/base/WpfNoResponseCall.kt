package com.wpf.app.quicknetwork.base

import retrofit2.Call

/**
 * Created by 王朋飞 on 2022/7/27.
 *
 */
open class WpfNoResponseCall<SResponse, FResponse>(
    rawCall: Call<BaseResponseIA<SResponse>>,
    fail: BaseResponseIA<FResponse>
) : WpfRealCall<BaseResponseIA<SResponse>, BaseResponseIA<FResponse>>(rawCall, fail)