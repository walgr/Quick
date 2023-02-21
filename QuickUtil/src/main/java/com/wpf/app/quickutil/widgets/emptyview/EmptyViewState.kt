package com.wpf.app.quickutil.widgets.emptyview

interface EmptyViewStateI {
    var curState: EmptyViewState
    fun changeState(state: EmptyViewState) {
        this.curState = state
    }
}

open class EmptyViewState : EmptyViewStateI {
    override var curState: EmptyViewState = NoError.curState
}

//正常状态
open class NoError : EmptyViewState() {

    companion object {
        val curState: EmptyViewState = NoError()
    }
}

//加载状态
open class Loading : EmptyViewState()

//成功状态
open class Success : EmptyViewState()

//网络异常
open class NetError(open val errorCode: Int = 0) : EmptyViewState()

//代码异常
open class ExceptionError(open val errorCode: Int = 0, open val e: Throwable? = null) :
    EmptyViewState()

//自定义异常
open class CustomError(open val errorCode: Int = 0, open val e: Throwable? = null) :
    EmptyViewState()