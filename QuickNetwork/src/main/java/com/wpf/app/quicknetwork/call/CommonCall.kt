package com.wpf.app.quicknetwork.call

import com.wpf.app.quicknetwork.base.BaseResponseIA
import com.wpf.app.quicknetwork.base.BaseResponseS
import retrofit2.Call

/**
 * Created by 王朋飞 on 2022/7/27.
 *
 */
open class CommonCall<SResponse>(rawCall: Call<BaseResponseS<SResponse>>, fail: BaseResponseS<Any>)
    : NoResponseCall<SResponse, Any>(rawCall, fail)