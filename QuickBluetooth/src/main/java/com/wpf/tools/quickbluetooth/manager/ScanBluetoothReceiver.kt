package com.wpf.tools.quickbluetooth.manager

import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class ScanBluetoothReceiver(private val onDeviceFind: ((device: BluetoothDevice) -> Unit)? = null) :
    BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        if (BluetoothDevice.ACTION_FOUND == intent?.action) {
            val device = intent.getParcelableExtra<BluetoothDevice>(BluetoothDevice.EXTRA_DEVICE)
            Log.e("蓝牙广播", "发现新设备:${device?.name ?: device?.address}")
            device?.let {
                onDeviceFind?.invoke(it)
            }
        }
    }
}