package com.wpf.app.quickutil.widgets.emptyview

interface EmptyViewStateI {
    var curState: EmptyViewState
    fun changeState(state: EmptyViewState) {
        this.curState = state
    }
}

open class EmptyViewState : EmptyViewStateI {
    override var curState: EmptyViewState = NoError
}

//正常状态
object NoError : EmptyViewState()

//加载状态
object Loading : EmptyViewState()

//成功状态
object Success : EmptyViewState()

object EmptyDataError : EmptyViewState()

//网络异常
open class NetError(open val errorCode: Int = 0) : EmptyViewState()

//代码异常
open class ExceptionError(open val errorCode: Int = 0, open val e: Throwable? = null) :
    EmptyViewState()

//自定义异常
open class CustomError(open val errorCode: Int = 0, open val e: Throwable? = null) :
    EmptyViewState()