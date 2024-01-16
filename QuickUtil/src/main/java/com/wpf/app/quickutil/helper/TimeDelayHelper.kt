package com.wpf.app.quickutil.helper

import java.util.Timer
import java.util.TimerTask

object TimeDelayHelper {
    private class Delay(//延时时间，单位秒
        var delayTime: Long, //注册时间，单位秒
        var startTime: Long, //结束后回调
        var timeout: () -> Unit
    )

    private val delayMap = HashMap<String, Delay>()
    private var interval: Timer? = null

    private fun initTime() {
        if (interval == null) {
            interval = Timer()
        }
        interval?.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                traversalDelayMap()
            }
        }, 0, 1000)
    }

    private fun traversalDelayMap() {
        if (delayMap.isEmpty()) return
        try {
            val curTime = System.currentTimeMillis() / 1000
            val newMap = HashMap(delayMap)
            val keySet: Set<String> = newMap.keys
            for (key in keySet) {
                val delay = delayMap[key] ?: continue
                if (curTime - delay.startTime >= delay.delayTime) {
                    //倒计时结束了
                    delay.startTime = curTime
                    delay.timeout.invoke()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * 注册定时器 退出页面前要销毁
     *
     * @param key       唯一key
     * @param delayTime 延迟时间 单位秒
     * @param timeout   时间到回调
     */
    fun registerTimeDelay(key: String, delayTime: Long, timeout: () -> Unit) {
        delayMap[key] = Delay(delayTime, System.currentTimeMillis() / 1000, timeout)
        initTime()
    }

    /**
     * 去除唯一key
     *
     * @param key 唯一key
     */
    fun unRegisterTimeDelay(key: String) {
        delayMap.remove(key)
        disposeOnMapEmpty()
    }

    private fun disposeOnMapEmpty() {
        if (delayMap.isEmpty()) {
            if (interval != null) {
                interval?.cancel()
                interval = null
            }
        }
    }

    /**
     * 是否所有定时
     */
    fun release() {
        delayMap.clear()
        disposeOnMapEmpty()
    }
}