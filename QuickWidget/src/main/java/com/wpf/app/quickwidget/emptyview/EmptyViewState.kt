package com.wpf.app.quickwidget.emptyview

import com.wpf.app.quickutil.helper.generic.forceTo

interface EmptyViewStateManager {
    val registerStateMap: MutableMap<Int, StateClassAndFun<out EmptyViewState>>
    var curState: EmptyViewState
    fun changeState(state: EmptyViewState) {
        this.curState = state
        val stateClassAndFun =
            registerStateMap[getClassFirstInterface<EmptyViewState>(state)] ?: return
        val dealStateFun: DealStateFun<EmptyViewState> = stateClassAndFun.dealStateFun as DealStateFun<EmptyViewState>
        val stateClass: Class<EmptyViewState> = stateClassAndFun.statusClass as Class<EmptyViewState>
        if (stateClass == state.javaClass) {
            dealStateFun.invoke(state)
        } else {
            when (state) {
                is NoErrorI -> {
                    dealStateFun.invoke(
                        stateClass.getDeclaredConstructor().newInstance()
                    )
                }
                is LoadingI -> {
                    dealStateFun.invoke(
                        stateClass.getDeclaredConstructor(Boolean::class.java).newInstance(state.listIsEmpty)
                    )
                }
                is SuccessI -> {
                    dealStateFun.invoke(
                        stateClass.getDeclaredConstructor().newInstance()
                    )
                }
                is StateNetError -> {
                    dealStateFun.invoke(
                        stateClass.getDeclaredConstructor(Int::class.java).newInstance(state.errorCode)
                    )
                }

                is StateCustomError -> {
                    dealStateFun.invoke(
                        stateClass.getDeclaredConstructor(
                            Int::class.java,
                            Exception::class.java
                        ).newInstance(state.errorCode, state.e)
                    )
                }

                else -> {
                    dealStateFun.invoke(stateClass.getDeclaredConstructor().newInstance())
                }
            }
        }
    }
}

inline fun <reified T : EmptyViewState> EmptyViewStateManager.register(noinline dealState: T.() -> Unit) {
    val key = getClassFirstInterface<T>()
    registerStateMap[key] = StateClassAndFun(T::class.java, dealState.forceTo<DealStateFun<EmptyViewState>>())
}

inline fun <reified T : EmptyViewState> EmptyViewStateManager.unRegister() {
    val key = getClassFirstInterface<T>()
    registerStateMap.remove(key)
}

/**
 * 找到最终的接口类[NoErrorI,LoadingI,SuccessI,EmptyDataI,CustomErrorI]
 */
inline fun <reified T : EmptyViewState> getClassFirstInterface(state: EmptyViewState? = null): Int {
    var hashCode = T::class.java.hashCode()
    var curObj: Class<*> = state?.javaClass ?: T::class.java
    do {
        if (!curObj.interfaces.isNullOrEmpty()) {
            hashCode = curObj.interfaces.first().hashCode()
            break
        }
        curObj = curObj.superclass
    } while (curObj != Any::class.java)
    return hashCode
}

typealias DealStateFun<T> = T.() -> Unit

class StateClassAndFun<T>(val statusClass: Class<T>, val dealStateFun: DealStateFun<T>)

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

//自定义异常
interface CustomErrorI : EmptyViewState {
    val errorCode: Int
    val e: Throwable?
}

open class StateCustomError(
    override val errorCode: Int = 0,
    override val e: Throwable? = null,
) : CustomErrorI

open class StateNetError(
    override val errorCode: Int = 0,
) : StateCustomError(errorCode)