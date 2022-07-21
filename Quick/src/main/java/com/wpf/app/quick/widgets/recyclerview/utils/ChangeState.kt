package com.wpf.app.quick.widgets.recyclerview.utils

/**
 * Created by 王朋飞 on 2022/7/21.
 *
 */
interface ChangeState<State> {
    val curState: State
    fun onChange(state: State)
}