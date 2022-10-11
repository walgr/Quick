package com.wpf.app.quicknetwork.call

import com.wpf.app.quicknetwork.base.BaseResponseS
import retrofit2.Call
import retrofit2.CallAdapter
import java.lang.reflect.Type

/**
 * Created by 王朋飞 on 2022/7/22.
 *
 */
class CommonCallAdapter<SResponse>(
    private val responseType: Type,
    sResponse: Class<SResponse>,
) : CallAdapter<BaseResponseS<SResponse>, CommonCall<SResponse>> {

    override fun adapt(call: Call<BaseResponseS<SResponse>>) = CommonCall(call, BaseResponseS())

    override fun responseType() = responseType
}