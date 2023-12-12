package com.wpf.app.quicknetwork.base

/**
 * Created by 王朋飞 on 2022/7/22.
 * 注解使用此类，移动需要修改注解代码
 */
abstract class BaseRequest<SResponse, FResponse> {

    internal var funBefore = {}

    internal var funSuccess = { _: SResponse? -> }

    internal var funFail = { _: FResponse? -> }

    internal var funAfter = {}

    open fun before(onBefore: () -> Unit): BaseRequest<SResponse, FResponse> {
        funBefore = onBefore
        return this
    }

    open fun success(onSuccess: (SResponse?) -> Unit): BaseRequest<SResponse, FResponse> {
        funSuccess = onSuccess
        return this
    }

    open fun fail(onFail: (FResponse?) -> Unit): BaseRequest<SResponse, FResponse> {
        funFail = onFail
        return this
    }

    open fun after(onAfter: () -> Unit): BaseRequest<SResponse, FResponse> {
        funAfter = onAfter
        return this
    }

    internal abstract fun isSuccess(response: SResponse?): Boolean

    internal abstract fun failMessage(response: FResponse?): String
}