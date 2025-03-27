package com.wpf.tools.quickbluetooth.manager

import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.bluetooth.BluetoothSocket
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.util.Log
import com.wpf.tools.quickbluetooth.permisssion.RequestPermissionHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.InputStream
import java.io.OutputStream
import java.util.UUID

typealias DeviceConnectChangeEvent = (device: BluetoothDevice, isConnect: Boolean) -> Unit

enum class BlueTypeName(private val type: Int, private val nameStr: String) {
    DEVICE_TYPE_UNKNOWN(0, "未知"),
    DEVICE_TYPE_CLASSIC(1, "传统蓝牙"),
    DEVICE_TYPE_LE(2, "低功耗蓝牙"),
    DEVICE_TYPE_DUAL(3, "双模蓝牙"),
    ;

    companion object {
        fun getNameByType(type: Int): String {
            return BlueTypeName.entries.find { it.type == type }?.nameStr ?: "未知"
        }
    }
}

object QuickBluetoothManager {

    private const val IS_DEBUG = true

    private fun log(msg: String) {
        if (!IS_DEBUG) return
        Log.e("蓝牙管理器", msg)
    }

    private const val REQUEST_ENABLE_BT = 7777

    var bluetoothAdapter: BluetoothAdapter? = null

    /**
     * 初始化获取BluetoothAdapter
     */
    private var eventReceiver: BluetoothEventReceiver? = null
    fun initAdapter(context: Context) {
        if (bluetoothAdapter == null) {
            bluetoothAdapter = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                (context.getSystemService(Context.BLUETOOTH_SERVICE) as? BluetoothManager)?.adapter
            } else {
                BluetoothAdapter.getDefaultAdapter()
            }
        }
        val intentFilter = IntentFilter(BluetoothDevice.ACTION_BOND_STATE_CHANGED)
        intentFilter.addAction(BluetoothDevice.ACTION_ACL_CONNECTED)
        intentFilter.addAction(BluetoothDevice.ACTION_ACL_DISCONNECTED)
        eventReceiver = BluetoothEventReceiver()
        context.registerReceiver(eventReceiver, intentFilter)
    }

    fun hasBluetooth(): Boolean {
        return bluetoothAdapter != null
    }

    fun isEnable(): Boolean {
        return bluetoothAdapter?.isEnabled == true
    }

    fun requestOpen(context: Activity) {
        RequestPermissionHelper.request(context) {
            val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            context.startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT)
        }
    }

    fun getConnectDevices(): Set<BluetoothDevice>? {
        return bluetoothAdapter?.bondedDevices
    }

    private var scanReceiver: ScanBluetoothReceiver? = null
    fun scanNewDevice(context: Activity, onDeviceFind: (device: BluetoothDevice) -> Unit) {
        RequestPermissionHelper.request(context) {
            val intentFilter = IntentFilter(BluetoothDevice.ACTION_FOUND)
            if (scanReceiver != null) {
                context.unregisterReceiver(scanReceiver)
            }
            scanReceiver = ScanBluetoothReceiver(onDeviceFind)
            context.registerReceiver(scanReceiver, intentFilter)
            if (bluetoothAdapter?.isDiscovering == true) {
                bluetoothAdapter?.cancelDiscovery()
            }
            if (bluetoothAdapter?.isDiscovering == false) {
                bluetoothAdapter?.startDiscovery()
            }
        }
    }

    fun stopScan(context: Activity) {
        scanReceiver?.let {
            context.unregisterReceiver(it)
        }
        scanReceiver = null
        if (bluetoothAdapter?.isDiscovering == true) {
            bluetoothAdapter?.cancelDiscovery()
        }
    }

    fun bondDevice(
        device: BluetoothDevice,
        onFail: ((e: Throwable) -> Unit)? = null,
        onSuccess: (() -> Unit)? = null
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            if (device.bondState != BluetoothDevice.BOND_BONDED) {
                val success = device.createBond()
                log("开始配对设备:${device.name}")
                if (success) {
                    log("配对设备成功:${device.name}")
                    launch(Dispatchers.Main) {
                        onSuccess?.invoke()
                    }
                } else {
                    log("配对设备失败:${device.name}")
                    launch(Dispatchers.Main) {
                        onFail?.invoke(Throwable("设备配对失败"))
                    }
                }
            } else {
                log("设备已配对:${device.name}")
                launch(Dispatchers.Main) {
                    onSuccess?.invoke()
                }
            }
        }
    }

    fun connectDevice(
        device: BluetoothDevice,
        onFail: ((e: Throwable) -> Unit)? = null,
        onSuccess: (inputS: InputStream, out: OutputStream) -> Unit
    ) {
        var socket: BluetoothSocket? = null
        bondDevice(device, onFail) {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    log("设备支持uuids:${getDeviceSupportUUID(device)?.joinToString(",")}")
                    socket =
                        device.createRfcommSocketToServiceRecord(UUID.fromString("0000110b-0000-1000-8000-00805f9b34fb"))
                    log("开始连接设备:${device.name}")
                    socket.connect()
                    log("连接设备成功:${device.name}")
                    launch(Dispatchers.Main) {
                        onSuccess(socket.inputStream, socket.outputStream)
                    }
                } catch (e: Exception) {
                    log("连接设备失败:${device.name},原因:${e.message}")
                    launch(Dispatchers.Main) {
                        onFail?.invoke(e)
                    }
                } finally {
                    runCatching {
                        socket?.close()
                    }
                }
            }
        }
    }

    fun getDeviceSupportUUID(device: BluetoothDevice): List<UUID>? {
        device.fetchUuidsWithSdp()
        return device.uuids?.map { it.uuid }
    }

    val connectChangeEventList: MutableMap<String, DeviceConnectChangeEvent> = mutableMapOf()
    fun registerDeviceConnectChangeEvent(
        context: Activity,
        connectChangeEvent: DeviceConnectChangeEvent
    ) {
        this.connectChangeEventList[getActivityKey(context)] = connectChangeEvent
    }

    fun onDestroy(context: Activity) {
        stopScan(context)
        connectChangeEventList.remove(getActivityKey(context))
    }

    private fun getActivityKey(context: Activity): String {
        return context.packageName + context.localClassName + context.hashCode()
    }

}