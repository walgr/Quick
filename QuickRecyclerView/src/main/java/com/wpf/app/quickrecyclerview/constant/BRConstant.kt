package com.wpf.app.quickrecyclerview.constant

/**
 * Created by 王朋飞 on 2022/7/13.
 * 不能混淆此类
 */
object BRConstant {
    var activity = 0
    var fragment = 0
    var dialog = 0
    var view = 0
    var viewModel = 0
    var data = 0
    var adapter = 0
    var position = 0

    fun initByBR(br: Class<*>) {
        br.fields.forEach { brF ->
            this::class.java.declaredFields.find { it.name == brF.name }?.set(this, brF.get(null))
        }
    }
}