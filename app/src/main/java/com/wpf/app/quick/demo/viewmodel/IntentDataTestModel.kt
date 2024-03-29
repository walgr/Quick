package com.wpf.app.quick.demo.viewmodel

import android.annotation.SuppressLint
import android.util.Log
import android.widget.TextView
import com.wpf.app.quick.demo.R
import com.wpf.app.quick.annotations.bind.BindView
import com.wpf.app.quick.demo.databinding.ActivityDataTestBinding
import com.wpf.app.quick.demo.model.MyMessage
import com.wpf.app.quick.activity.viewmodel.QuickVBModel
import com.wpf.app.quick.demo.IntentDataTestActivity
import com.wpf.app.quickbind.annotations.AutoGet

/**
 * Created by 王朋飞 on 2022/6/13.
 */
class IntentDataTestModel : QuickVBModel<IntentDataTestActivity, ActivityDataTestBinding>() {
    private val TAG = "IntentDataTestViewModel"

    @AutoGet
    var intD = 0

    @AutoGet
    var byteD: Byte = 0

    @AutoGet
    var floatD = 0f

    @AutoGet
    var doubleD = 0.0

    @AutoGet
    var charD = 'a'

    @AutoGet
    var data: MyMessage? = null

    @AutoGet
    var map: Map<String, Any>? = null

    @AutoGet
    var list: List<Any>? = null

    @AutoGet
    var array: Array<Any>? = null

    @AutoGet
    var listS: List<MyMessage>? = null

    @AutoGet
    var arrayS: Array<MyMessage>? = null

    @SuppressLint("NonConstantResourceId", "StaticFieldLeak")
    @BindView(R.id.title1)
    var title: TextView? = null

    override fun onBindingCreated(view: ActivityDataTestBinding) {
        printData()
    }

    @SuppressLint("SetTextI18n")
    private fun printData() {
        Log.i(TAG, intD.toString() + "")
        Log.i(TAG, floatD.toString() + "")
        Log.i(TAG, doubleD.toString() + "")
        Log.i(TAG, charD.toString() + "")
        Log.i(TAG, byteD.toString() + "")
        Log.i(TAG, data.toString() + "")
        Log.i(TAG, map.toString() + "")
        Log.i(TAG, list.toString() + "")
        Log.i(TAG, array!![0].toString() + "")
        Log.i(TAG, listS!![0].userName + "")
        Log.i(TAG, arrayS!![0].layoutId.toString() + "")
        title?.text = "数据测试页ViewModel"
    }
}