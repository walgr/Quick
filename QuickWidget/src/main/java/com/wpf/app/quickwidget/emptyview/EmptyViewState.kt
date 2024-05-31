package com.wpf.app.quickwidget.emptyview

import com.wpf.app.quickutil.other.forceTo

interface EmptyViewStateManager {
    val registerStateMap: MutableMap<Int, DealStateFun<EmptyViewState>>
    var curState: EmptyViewState
    fun changeState(state: EmptyViewState) {
        this.curState = state
        val dealStateFun = registerStateMap[state::class.java.interfaces.first().hashCode()] ?: return
        val stateClass: Class<EmptyViewState> = dealStateFun.javaClass.methods.find { it.name == "invoke" }!!.parameterTypes.first().forceTo()
        if (stateClass == state.javaClass) {
            dealStateFun.invoke(state)
        } else {
            dealStateFun.invoke(stateClass.getDeclaredConstructor().newInstance())
        }
    }
}

inline fun <reified T : EmptyViewState> EmptyViewStateManager.register(noinline dealState: T.() -> Unit) {
    val key = T::class.java.interfaces.first().hashCode()
    registerStateMap[key] = dealState as DealStateFun<EmptyViewState>
}

inline fun <reified T : EmptyViewState> EmptyViewStateManager.unRegister() {
    val key = T::class.hashCode()
    registerStateMap.remove(key)
}

typealias DealStateFun<T> = T.() -> Unit

interface EmptyViewState

//正常状态
interface NoErrorI : EmptyViewState
object StateNoError : NoErrorI

//加载状态
interface LoadingI : EmptyViewState {
    var listIsEmpty: Boolean
}
object StateLoading : LoadingI {
    override var listIsEmpty: Boolean = true

    fun new(listIsEmpty: Boolean): StateLoading {
        val stateLoading = StateLoading
        stateLoading.listIsEmpty = listIsEmpty
        return stateLoading
    }
}

//成功状态
interface SuccessI : EmptyViewState
object StateSuccess : SuccessI

interface EmptyDataI : EmptyViewState
object StateEmptyData : EmptyDataI

//网络异常
interface NetErrorI : EmptyViewState {
    val errorCode: Int
}

open class StateNetError(
    override val errorCode: Int = 0,
) : NetErrorI

//自定义异常
interface CustomErrorI : EmptyViewState {
    val errorCode: Int
    val e: Throwable?
}

open class StateCustomError(
    override val errorCode: Int = 0,
    override val e: Throwable? = null,
) : CustomErrorI

//代码异常
interface ExceptionErrorI : CustomErrorI
open class StateExceptionError(
    override val errorCode: Int = 0,
    override val e: Throwable? = null,
) : ExceptionErrorI