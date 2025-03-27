package com.wpf.tools.quickbluetooth.manager

import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class BluetoothEventReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        if (BluetoothDevice.ACTION_ACL_CONNECTED == intent?.action) {
            val device = intent.getParcelableExtra<BluetoothDevice>(BluetoothDevice.EXTRA_DEVICE)
            Log.e("蓝牙广播", "设备已连接:${device?.name ?: device?.address}")
        } else if (BluetoothDevice.ACTION_ACL_DISCONNECTED == intent?.action) {
            val device = intent.getParcelableExtra<BluetoothDevice>(BluetoothDevice.EXTRA_DEVICE)
            Log.e("蓝牙广播", "设备已断开:${device?.name ?: device?.address}")
        } else if (BluetoothDevice.ACTION_BOND_STATE_CHANGED == intent?.action) {
            val device = intent.getParcelableExtra<BluetoothDevice>(BluetoothDevice.EXTRA_DEVICE)
            Log.e("蓝牙广播", "设备配对状态已改变:${device?.name ?: device?.address}")
            device?.let {
                QuickBluetoothManager.connectChangeEventList.values.forEach {
                    it.invoke(device, device.bondState == BluetoothDevice.BOND_BONDED)
                }
            }
        }
    }
}