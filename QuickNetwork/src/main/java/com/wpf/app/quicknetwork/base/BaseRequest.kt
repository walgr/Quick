package com.wpf.app.quicknetwork.base

import com.wpf.app.quicknetwork.helper.NetworkResponseInterceptor.beforeListDefault
import com.wpf.app.quicknetwork.helper.NetworkResponseInterceptor.beforeListForce
import com.wpf.app.quicknetwork.helper.NetworkResponseInterceptor.errorListDefault
import com.wpf.app.quicknetwork.helper.NetworkResponseInterceptor.errorListForce
import com.wpf.app.quicknetwork.helper.NetworkResponseInterceptor.failListDefault
import com.wpf.app.quicknetwork.helper.NetworkResponseInterceptor.failListForce
import com.wpf.app.quicknetwork.helper.NetworkResponseInterceptor.finallyListDefault
import com.wpf.app.quicknetwork.helper.NetworkResponseInterceptor.finallyListForce
import com.wpf.app.quicknetwork.helper.NetworkResponseInterceptor.successBeforeListDefault
import com.wpf.app.quicknetwork.helper.NetworkResponseInterceptor.successBeforeListForce
import com.wpf.app.quicknetwork.helper.NetworkResponseInterceptor.successListDefault
import com.wpf.app.quicknetwork.helper.NetworkResponseInterceptor.successListForce

/**
 * Created by 王朋飞 on 2022/7/22.
 * 注解使用此类，移动需要修改注解代码
 */
abstract class BaseRequest<SResponse, FResponse> {

    internal var funBefore = {
        beforeListForce.forEach {
            it.invoke()
        }
        if (!beforeUnique) {
            beforeListDefault.forEach {
                it.invoke()
            }
        }
        beforeList.forEach { it.invoke() }
    }

    internal var funSuccessBefore = { data: SResponse ->
        successBeforeListForce.forEach {
            it.invoke(data as Any)
        }
        if (!successBeforeUnique) {
            successBeforeListDefault.forEach {
                it.invoke(data as Any)
            }
        }
        successBeforeList.forEach {
            it.invoke(data)
        }
    }

    internal var funSuccess = { data: SResponse ->
        successListForce.forEach {
            it.invoke(data as Any)
        }
        if (!successUnique) {
            successListDefault.forEach {
                it.invoke(data as Any)
            }
        }
        successList.forEach {
            it.invoke(data)
        }
    }

    internal var funFail = { data: FResponse? ->
        failListForce.forEach {
            it.invoke(data)
        }
        if (!failUnique) {
            failListDefault.forEach {
                it.invoke(data)
            }
        }
        failList.forEach {
            it.invoke(data)
        }
    }

    internal var funError = { t: Throwable ->
        errorListForce.forEach {
            it.invoke(t)
        }
        if (!errorUnique) {
            errorListDefault.forEach {
                it.invoke(t)
            }
        }
        errorList.forEach {
            it.invoke(t)
        }
    }

    internal var funFinally = {
        finallyListForce.forEach {
            it.invoke()
        }
        if (!finallyUnique) {
            finallyListDefault.forEach {
                it.invoke()
            }
        }
        finallyList.forEach { it.invoke() }
    }

    private val beforeList = mutableListOf<() -> Unit>()
    private val successBeforeList = mutableListOf<(data: SResponse) -> Unit>()
    private val successList = mutableListOf<(data: SResponse) -> Unit>()
    private val failList = mutableListOf<(data: FResponse?) -> Unit>()
    private val errorList = mutableListOf<(data: Throwable) -> Unit>()
    private val finallyList = mutableListOf<() -> Unit>()

    private var beforeUnique = false
    private var successBeforeUnique = false
    private var successUnique = false
    private var failUnique = false
    private var errorUnique = false
    private var finallyUnique = false

    open fun before(
        unique: Boolean = false,
        onBefore: () -> Unit,
    ): BaseRequest<SResponse, FResponse> {
        this.beforeUnique = unique
        if (unique) beforeList.clear()
        beforeList.add(onBefore)
        return this
    }

    @Suppress("unused")
    open fun successBefore(
        unique: Boolean = false,
        onSuccessBefore: (SResponse) -> Unit,
    ): BaseRequest<SResponse, FResponse> {
        this.successBeforeUnique = unique
        if (unique) successBeforeList.clear()
        successBeforeList.add(onSuccessBefore)
        return this
    }

    @Suppress("unused")
    open fun success(
        unique: Boolean = false,
        onSuccess: (SResponse) -> Unit,
    ): BaseRequest<SResponse, FResponse> {
        this.successUnique = unique
        if (unique) successList.clear()
        successList.add(onSuccess)
        return this
    }

    @Suppress("unused")
    open fun fail(
        unique: Boolean = false,
        onFail: (FResponse?) -> Unit,
    ): BaseRequest<SResponse, FResponse> {
        this.failUnique = unique
        if (unique) failList.clear()
        failList.add(onFail)
        return this
    }

    open fun error(
        unique: Boolean = false,
        onError: (Throwable) -> Unit,
    ): BaseRequest<SResponse, FResponse> {
        this.errorUnique = unique
        if (unique) errorList.clear()
        errorList.add(onError)
        return this
    }

    open fun finally(
        unique: Boolean = false,
        onFinally: () -> Unit,
    ): BaseRequest<SResponse, FResponse> {
        this.finallyUnique = unique
        if (unique) finallyList.clear()
        finallyList.add(onFinally)
        return this
    }

    internal abstract fun isSuccess(response: SResponse?): Boolean

    internal abstract fun failMessage(response: FResponse?): String
}