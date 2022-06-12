package com.wpf.app.quick

import android.annotation.SuppressLint
import android.view.View
import android.widget.TextView
import com.wpf.app.quick.base.activity.ViewModelBindingActivity
import com.wpf.app.quick.base.helper.FindView
import com.wpf.app.quick.base.viewmodel.BindingViewModel
import com.wpf.app.quick.databinding.ActivityMainBinding
import com.wpf.app.quick.model.MyMessage
import com.wpf.app.quick.model.TestModel

class MainActivity :
    ViewModelBindingActivity<BindingViewModel<ActivityMainBinding>, ActivityMainBinding>(
        R.layout.activity_main,
        activityTitle = "快捷"
    ) {

    @SuppressLint("NonConstantResourceId")
    @FindView(R.id.spTextView1, bindSp = "绑定的SpKey1", default = "默认值1")
    val bindSp1: TextView? = null
    @SuppressLint("NonConstantResourceId")
    @FindView(R.id.spTextView2, bindSp = "绑定的SpKey2", default = "默认值2")
    val bindSp2: TextView? = null
    @SuppressLint("NonConstantResourceId")
    @FindView(R.id.spTextView3, bindSp = "绑定的SpKey3", default = "默认值3")
    val bindSp3: TextView? = null

    fun gotoGlide(view: View) {
        startActivity(GlideImageTestActivity::class.java)
    }

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
            "array" to arrayOf("71", "72"),
            "listS" to listOf(MyMessage(userName = "81"), MyMessage(userName = "82")),
            "listP" to listOf(TestModel(text = "91"), TestModel(text = "92")),
            "arrayS" to arrayOf(MyMessage(userName = "101"), MyMessage(userName = "102")),
            "arrayP" to arrayOf(TestModel(text = "111"), TestModel(text = "112")), //暂不支持
        ))
    }

    @SuppressLint("SetTextI18n")
    override fun initView(viewDataBinding: ActivityMainBinding?) {
        super.initView(viewDataBinding)
        bindSp1?.postDelayed({
            bindSp1.text = System.currentTimeMillis().toString()
        }, 1000)
        bindSp2?.postDelayed({
            bindSp2.text = System.currentTimeMillis().toString()
        }, 1000)
        bindSp3?.postDelayed({
            bindSp3.text = System.currentTimeMillis().toString()
        }, 1000)
    }
}