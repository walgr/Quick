package com.wpf.app.quick

import android.view.View
import com.wpf.app.quick.base.activity.ViewModelBindingActivity
import com.wpf.app.quick.databinding.ActivityMainBinding
import com.wpf.app.quick.model.MyMessage
import com.wpf.app.quick.model.TestModel
import com.wpf.app.quick.viewmodel.MainViewModel

class MainActivity :
    ViewModelBindingActivity<MainViewModel, ActivityMainBinding>(
        R.layout.activity_main,
        activityTitle = "快捷"
    ) {

    fun gotoList(view: View) {
        startActivity(RecyclerViewTestActivity::class.java)
    }

    fun gotoData(view: View) {
        startActivity(IntentDataTestActivity::class.java, data = mapOf(
            "activityTitle" to "数据测试页",
            "intD" to 2,
            "floatD" to 3f,
            "doubleD" to 4.0,
            "charD" to 'b',
            "byteD" to 6.toByte(),
            "data" to MyMessage(userName = "31"),
            "data1" to TestModel(text = "41"),
            "map" to mapOf("map1" to "51"),
            "list" to listOf("61"),
            "array" to arrayOf("71")
        ))
    }
}