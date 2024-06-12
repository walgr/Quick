package com.wpf.app.quicknetwork.base

/**
 * Created by 王朋飞 on 2022/7/22.
 * 注解使用此类，移动需要修改注解代码
 */
abstract class BaseRequest<SResponse, FResponse> {

    internal var funBefore = {
        beforeList.forEach { it.invoke() }
    }

    internal var funSuccessBefore = { data: SResponse ->
        successBeforeList.forEach {
            it.invoke(data)
        }
    }

    internal var funSuccess = { data: SResponse ->
        successList.forEach {
            it.invoke(data)
        }
    }

    internal var funFail = { data: FResponse? ->
        failList.forEach {
            it.invoke(data)
        }
    }

    internal var funError = { t: Throwable ->
        errorList.forEach {
            it.invoke(t)
        }
    }

    internal var funAfter = {
        afterList.forEach { it.invoke() }
    }

    private val beforeList = mutableListOf<() -> Unit>()
    private val successBeforeList = mutableListOf<(data: SResponse) -> Unit>()
    private val successList = mutableListOf<(data: SResponse) -> Unit>()
    private val failList = mutableListOf<(data: FResponse?) -> Unit>()
    private val errorList = mutableListOf<(data: Throwable) -> Unit>()
    private val afterList = mutableListOf<() -> Unit>()

    open fun before(
        unique: Boolean = false,
        onBefore: () -> Unit,
    ): BaseRequest<SResponse, FResponse> {
        if (unique) beforeList.clear()
        beforeList.add(onBefore)
        return this
    }

    open fun successBefore(
        unique: Boolean = false,
        onSuccessBefore: (SResponse) -> Unit,
    ): BaseRequest<SResponse, FResponse> {
        if (unique) successBeforeList.clear()
        successBeforeList.add(onSuccessBefore)
        return this
    }

    open fun success(
        unique: Boolean = false,
        onSuccess: (SResponse) -> Unit,
    ): BaseRequest<SResponse, FResponse> {
        if (unique) successList.clear()
        successList.add(onSuccess)
        return this
    }

    open fun fail(
        unique: Boolean = false,
        onFail: (FResponse?) -> Unit,
    ): BaseRequest<SResponse, FResponse> {
        if (unique) failList.clear()
        failList.add(onFail)
        return this
    }

    open fun error(
        unique: Boolean = false,
        onError: (Throwable) -> Unit,
    ): BaseRequest<SResponse, FResponse> {
        if (unique) errorList.clear()
        errorList.add(onError)
        return this
    }

    open fun after(
        unique: Boolean = false,
        onAfter: () -> Unit,
    ): BaseRequest<SResponse, FResponse> {
        if (unique) afterList.clear()
        afterList.add(onAfter)
        return this
    }

    internal abstract fun isSuccess(response: SResponse?): Boolean

    internal abstract fun failMessage(response: FResponse?): String
}