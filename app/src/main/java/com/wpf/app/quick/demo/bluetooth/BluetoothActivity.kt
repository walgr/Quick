package com.wpf.app.quick.demo.bluetooth

import android.app.Activity
import android.bluetooth.BluetoothDevice
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.isVisible
import com.wpf.app.quick.QuickActivity2
import com.wpf.app.quick.annotations.bind.BindData2View
import com.wpf.app.quick.annotations.bind.BindView
import com.wpf.app.quick.demo.R
import com.wpf.app.quickbind.helper.binddatahelper.ItemClick
import com.wpf.app.quickrecyclerview.QuickAdapter
import com.wpf.app.quickrecyclerview.QuickRecyclerView
import com.wpf.app.quickrecyclerview.ability.helper.list
import com.wpf.app.quickrecyclerview.data.QuickAbilityData
import com.wpf.app.quickrecyclerview.data.QuickBindData
import com.wpf.app.quickrecyclerview.holder.QuickViewHolder
import com.wpf.app.quickutil.ability.base.QuickAbility
import com.wpf.app.quickutil.ability.ex.contentView
import com.wpf.app.quickutil.init.ToastHelper
import com.wpf.app.quickutil.run.itemClick
import com.wpf.app.quickutil.utils.LogUtil
import com.wpf.app.quickwork.ability.helper.title
import com.wpf.tools.quickbluetooth.manager.BlueTypeName
import com.wpf.tools.quickbluetooth.manager.QuickBluetoothManager
import com.wpf.tools.quickbluetooth.permisssion.RequestPermissionHelper

enum class BluetoothDeviceState(state: String) {
    NO_BOND("未配对"),
    BONDED("已配对"),
    NO_CONNECT("未连接"),
    CONNECTED("已连接"),
}

class BluetoothDeviceItem : QuickAbilityData(
    contentView(R.layout.holder_blue_item),
    autoSet = true
) {
    var sort = 0
    var name: String = ""
    var type: String = ""
    var state: String = "状态:未配对"
    var device: BluetoothDevice? = null

    @BindView(R.id.btnConnect)
    var btn: Button? = null
    @BindView(R.id.state)
    var stateView: TextView? = null

    @BindData2View(id = R.id.btnConnect, helper = ItemClick::class)
    val connectClick = itemClick {
        device?.let { device ->
            if (it.tag == "配对") {
                ToastHelper.show("开始配对")
                LogUtil.e("开始配对设备:${device.name}")
                QuickBluetoothManager.stopScan(it.context as Activity)
                QuickBluetoothManager.bondDevice(device)
            } else if (it.tag == "发送数据") {
                ToastHelper.show("开始发送数据")
                LogUtil.e("开始发送数据到设备:${device.name}")
                QuickBluetoothManager.stopScan(it.context as Activity)
                QuickBluetoothManager.connectDevice(device) { input, out ->
                    out.write("Hello".toByteArray())
                }
            }
        }
    }

    override fun onBindViewHolder(
        adapter: QuickAdapter,
        viewHolder: QuickViewHolder<QuickBindData>,
        position: Int
    ) {
        super.onBindViewHolder(adapter, viewHolder, position)
//        btn = getView()?.findViewById<Button>(R.id.btnConnect)
//        stateView = getView()?.findViewById<TextView>(R.id.state)
        Log.e("-------", "刷新view:${btn}---text:${btn?.text}----状态:${this.deviceState}")
        chaneState(this.deviceState)
    }

    var deviceState: BluetoothDeviceState = BluetoothDeviceState.NO_BOND
    fun chaneState(state: BluetoothDeviceState) {
        this.deviceState = state
        btn?.isVisible = true
        if (state == BluetoothDeviceState.BONDED) {
            this.sort = 1
            btn?.text = "发送数据"
            btn?.tag = "发送数据"
            this.state = "状态:已配对"
        } else if (state == BluetoothDeviceState.NO_BOND) {
            this.sort = 0
            btn?.text = "配对"
            btn?.tag = "配对"
            this.state = "状态:未配对"
        }
        stateView?.text = this.state
    }
}

class BluetoothActivity : QuickActivity2() {

    private var list: QuickRecyclerView? = null
    private val bluetoothDevices by lazy {
        QuickBluetoothManager.getConnectDevices()?.map {
            BluetoothDeviceItem().apply {
                name = it.name?.ifEmpty { it.address } ?: it.address
                type = BlueTypeName.getNameByType(it.type)
                device = it
                deviceState = BluetoothDeviceState.BONDED
            }
        }
    }

    override fun initAbility(): List<QuickAbility> {
        return contentView<LinearLayout> {
            title("蓝牙")
            list = list(dataList = bluetoothDevices)
        }
    }

    override fun initView(view: View) {
        super.initView(view)
        QuickBluetoothManager.registerDeviceConnectChangeEvent(this) { device, isConnect ->
            this.list?.getRealTypeData<BluetoothDeviceItem>()
                ?.find { it.device?.address == device.address }
                ?.chaneState(if (isConnect) BluetoothDeviceState.BONDED else BluetoothDeviceState.NO_BOND)
            this.list?.getRealTypeData<BluetoothDeviceItem>()
                ?.sortWith { item1, item2 -> item2.sort - item1.sort }
            this.list?.adapter?.notifyDataSetChanged()
        }
        QuickBluetoothManager.scanNewDevice(this) { device ->
            if (device.name?.isNotEmpty() == true) {
                if (this.list?.getRealTypeData<BluetoothDeviceItem>()
                        ?.find { it.device?.address == device.address } == null
                ) {
                    this.list?.addData(BluetoothDeviceItem().apply {
                        name = device.name?.ifEmpty { device.address } ?: device.address
                        type = BlueTypeName.getNameByType(device.type)
                        this.device = device
                        deviceState = BluetoothDeviceState.NO_BOND
                    })
                    this.list?.adapter?.notifyDataSetChanged()
                }
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        RequestPermissionHelper.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onDestroy() {
        super.onDestroy()
        QuickBluetoothManager.onDestroy(this)
    }
}