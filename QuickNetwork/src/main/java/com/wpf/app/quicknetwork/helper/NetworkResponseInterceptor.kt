package com.wpf.app.quicknetwork.helper

object NetworkResponseInterceptor {

    /**
     * 默认执行
     */
    internal val beforeListDefault = mutableListOf<() -> Unit>()
    internal val successBeforeListDefault = mutableListOf<(data: Any) -> Unit>()
    internal val successListDefault = mutableListOf<(data: Any) -> Unit>()
    internal val failListDefault = mutableListOf<(data: Any?) -> Unit>()
    internal val errorListDefault = mutableListOf<(data: Throwable) -> Unit>()
    internal val finallyListDefault = mutableListOf<() -> Unit>()

    @Suppress("unused")
    fun registerBeforeDefault(
        vararg onBefore: () -> Unit,
    ) {
        beforeListDefault.addAll(onBefore)
    }

    @Suppress("unused")
    fun registerBeforeDefault(
        onBefore: () -> Unit,
    ) {
        beforeListDefault.add(onBefore)
    }

    @Suppress("UNCHECKED_CAST", "unused")
    fun <SResponse : Any> registerSuccessBeforeDefault(
        vararg onSuccessBefore: (SResponse) -> Unit,
    ) {
        successBeforeListDefault.addAll(onSuccessBefore as Array<(Any) -> Unit>)
    }

    @Suppress("UNCHECKED_CAST", "unused")
    fun <SResponse : Any> registerSuccessBeforeDefault(
        onSuccessBefore: (SResponse) -> Unit,
    ) {
        successBeforeListDefault.add(onSuccessBefore as (Any) -> Unit)
    }

    @Suppress("UNCHECKED_CAST", "unused")
    fun <SResponse : Any> registerSuccessDefault(
        vararg onSuccess: (SResponse) -> Unit,
    ) {
        successListDefault.addAll(onSuccess as Array<(Any) -> Unit>)
    }

    @Suppress("UNCHECKED_CAST", "unused")
    fun <SResponse : Any> registerSuccessDefault(
        onSuccess: (SResponse) -> Unit,
    ) {
        successListDefault.add(onSuccess as (Any) -> Unit)
    }

    @Suppress("UNCHECKED_CAST", "unused")
    fun <FResponse : Any> registerFailDefault(
        vararg onFail: (FResponse?) -> Unit,
    ) {
        failListDefault.addAll(onFail as Array<(Any?) -> Unit>)
    }

    @Suppress("UNCHECKED_CAST", "unused")
    fun <FResponse : Any> registerFailDefault(
        onFail: (FResponse?) -> Unit,
    ) {
        failListDefault.add(onFail as (Any?) -> Unit)
    }

    @Suppress("unused")
    fun registerErrorDefault(
        vararg onError: (Throwable) -> Unit,
    ) {
        errorListDefault.addAll(onError)
    }

    @Suppress("unused")
    fun registerErrorDefault(
        onError: (Throwable) -> Unit,
    ) {
        errorListDefault.add(onError)
    }

    @Suppress("unused")
    fun registerFinallyDefault(
        vararg onAfter: () -> Unit,
    ) {
        finallyListDefault.addAll(onAfter)
    }

    @Suppress("unused")
    fun registerFinallyDefault(
        onAfter: () -> Unit,
    ) {
        finallyListDefault.add(onAfter)
    }

    /**
     * 强制执行
     */
    internal val beforeListForce = mutableListOf<() -> Unit>()
    internal val successBeforeListForce = mutableListOf<(data: Any) -> Unit>()
    internal val successListForce = mutableListOf<(data: Any) -> Unit>()
    internal val failListForce = mutableListOf<(data: Any?) -> Unit>()
    internal val errorListForce = mutableListOf<(data: Throwable) -> Unit>()
    internal val finallyListForce = mutableListOf<() -> Unit>()

    @Suppress("unused")
    fun registerBeforeForce(
        vararg onBefore: () -> Unit,
    ) {
        beforeListForce.addAll(onBefore)
    }

    @Suppress("unused")
    fun registerBeforeForce(
        onBefore: () -> Unit,
    ) {
        beforeListForce.add(onBefore)
    }

    @Suppress("UNCHECKED_CAST", "unused")
    fun <SResponse : Any> registerSuccessBeforeForce(
        vararg onSuccessBefore: (SResponse) -> Unit,
    ) {
        successBeforeListForce.addAll(onSuccessBefore as Array<(Any) -> Unit>)
    }

    @Suppress("UNCHECKED_CAST", "unused")
    fun <SResponse : Any> registerSuccessBeforeForce(
        onSuccessBefore: (SResponse) -> Unit,
    ) {
        successBeforeListForce.add(onSuccessBefore as (Any) -> Unit)
    }

    @Suppress("UNCHECKED_CAST", "unused")
    fun <SResponse : Any> registerSuccessForce(
        vararg onSuccess: (SResponse) -> Unit,
    ) {
        successListForce.addAll(onSuccess as Array<(Any) -> Unit>)
    }

    @Suppress("UNCHECKED_CAST", "unused")
    fun <SResponse : Any> registerSuccessForce(
        onSuccess: (SResponse) -> Unit,
    ) {
        successListForce.add(onSuccess as (Any) -> Unit)
    }

    @Suppress("UNCHECKED_CAST", "unused")
    fun <FResponse : Any> registerFailForce(
        vararg onFail: (FResponse?) -> Unit,
    ) {
        failListForce.addAll(onFail as Array<(Any?) -> Unit>)
    }

    @Suppress("UNCHECKED_CAST", "unused")
    fun <FResponse : Any> registerFailForce(
        onFail: (FResponse?) -> Unit,
    ) {
        failListForce.add(onFail as (Any?) -> Unit)
    }

    @Suppress("unused")
    fun registerErrorForce(
        vararg onError: (Throwable) -> Unit,
    ) {
        errorListForce.addAll(onError)
    }

    @Suppress("unused")
    fun registerErrorForce(
        onError: (Throwable) -> Unit,
    ) {
        errorListForce.add(onError)
    }

    @Suppress("unused")
    fun registerFinallyForce(
        vararg onAfter: () -> Unit,
    ) {
        finallyListForce.addAll(onAfter)
    }

    @Suppress("unused")
    fun registerFinallyForce(
        onAfter: () -> Unit,
    ) {
        finallyListForce.add(onAfter)
    }
}