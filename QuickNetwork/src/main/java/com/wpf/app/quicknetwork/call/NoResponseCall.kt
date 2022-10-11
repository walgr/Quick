package com.wpf.app.quicknetwork.call

import com.wpf.app.quicknetwork.base.BaseResponseS
import retrofit2.Call

/**
 * Created by 王朋飞 on 2022/7/27.
 *
 */
open class NoResponseCall<SResponse, FResponse>(
    rawCall: Call<BaseResponseS<SResponse>>,
    fail: BaseResponseS<FResponse>
) : RealCall<BaseResponseS<SResponse>, BaseResponseS<FResponse>>(rawCall, fail)