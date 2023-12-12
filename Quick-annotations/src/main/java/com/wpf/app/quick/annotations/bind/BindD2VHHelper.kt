package com.wpf.app.quick.annotations.bind

/**
 * Created by 王朋飞 on 2022/7/6.
 */
interface BindD2VHHelper<VH, V, Data> {
    fun initView(viewHolder: VH?, view: V, data: Data)
}