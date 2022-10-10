package com.wpf.app.quicknetwork.base

/**
 * Created by 王朋飞 on 2022/7/22.
 *
 */
class WpfRequest<SResponse, FResponse> : BaseRequest<SResponse, FResponse>() {

    override fun isSuccess(response: SResponse?): Boolean {
        if (response is BaseResponseI<*,*>) {
            return response.isSuccess()
        }
        return response != null
    }

    override fun failMessage(response: FResponse?): String {
        return response?.toString() ?: "未知错误"
    }

}