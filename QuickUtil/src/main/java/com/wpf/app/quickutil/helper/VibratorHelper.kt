package com.wpf.app.quickutil.helper

import android.app.Service
import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager

object VibratorHelper {


    /**
     * 让手机振动milliseconds毫秒
     */
    fun vibrate(context: Context, milliseconds: Long) {
        val vib = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            (context.getSystemService(Service.VIBRATOR_MANAGER_SERVICE) as VibratorManager).defaultVibrator
        } else {
            context.getSystemService(Service.VIBRATOR_SERVICE) as Vibrator
        }
        if (vib.hasVibrator()) {  //判断手机硬件是否有振动器
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vib.vibrate(VibrationEffect.createOneShot(milliseconds, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                vib.vibrate(milliseconds)
            }
        }
    }
}