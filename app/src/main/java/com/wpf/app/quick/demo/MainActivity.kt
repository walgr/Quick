package com.wpf.app.quick.demo

import android.annotation.SuppressLint
import android.app.Activity
import android.view.View
import android.widget.TextView
import com.wpf.app.quick.activity.QuickViewBindingActivity
import com.wpf.app.quick.annotations.BindView
import com.wpf.app.quick.demo.databinding.ActivityMainBinding
import com.wpf.app.quick.demo.model.MyMessage
import com.wpf.app.quick.demo.model.TestModel
import com.wpf.app.quick.demo.viewmodel.MainModel
import com.wpf.app.quickbind.annotations.BindSp2View
import com.wpf.app.quickutil.startActivity

/**
 * Created by 王朋飞 on 2022/6/13.
 */
class MainActivity : QuickViewBindingActivity<MainModel, ActivityMainBinding>(R.layout.activity_main, titleName = "快捷") {

    @SuppressLint("NonConstantResourceId")
//    @BindSp2View(bindSp = "绑定的SpKey1", defaultValue = "默认值1")
    @BindView(R.id.spTextView1)
    var text1: TextView? = null

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.spTextView2)
    var text2: TextView? = null

    @SuppressLint("NonConstantResourceId")
    @BindSp2View(bindSp = "绑定的SpKey3", defaultValue = "默认值3")
    @BindView(R.id.spTextView3)
    var text3: TextView? = null

    @SuppressLint("SetTextI18n")
    override fun initView() {
        text1?.postDelayed(
            { text1?.text = System.currentTimeMillis().toString() + "" },
            1000
        )
        text2?.postDelayed(
            { text2?.text = System.currentTimeMillis().toString() + "" },
            1000
        )
        text3?.postDelayed(
            { text3?.text = System.currentTimeMillis().toString() + "" },
            1000
        )
    }

    fun gotoR2Test(view: View?) {
        try {
            startActivity(activityCls = Class.forName("com.wpf.app.r2test.R2TestActivity") as Class<Activity>)
        } catch (ignore: Exception) { }
    }

    fun gotoGlide(view: View?) {
        startActivity(activityCls = ViewPagerBindFragmentTestActivity::class.java)
    }

    fun gotoRefreshList(view: View?) {
        startActivity(activityCls = RefreshListTestActivity::class.java)
    }

    fun gotoSelectList(view: View?) {
        startActivity(activityCls = SelectListTestActivity::class.java)
    }

    fun gotoList(view: View?) {
        startActivity(activityCls = RecyclerViewTestActivity::class.java)
    }

    fun gotoData(view: View?) {
        startActivity(activityCls = IntentDataTestActivity::class.java, object : HashMap<String, Any?>() {
            init {
                put("activityTitle", "数据测试页")
                put("intD", 2)
                put("floatD", 3f)
                put("doubleD", 4.0)
                put("charD", 'b')
                put("byteD", 6.toByte())
                put("data", MyMessage("31"))
                put("data1", TestModel("41"))
                put("map", object : HashMap<String?, String?>() {
                    init {
                        put("map1", "51")
                    }
                })
                put("list", object : ArrayList<String?>() {
                    init {
                        add("61")
                        add("62")
                    }
                })
                put("array", arrayOf("71", "72"))
                put("listS", object : ArrayList<MyMessage?>() {
                    init {
                        add(MyMessage("81"))
                        add(MyMessage("82"))
                    }
                })
                put("listP", object : ArrayList<TestModel?>() {
                    init {
                        add(TestModel("91"))
                        add(TestModel("92"))
                    }
                })
                put("arrayS", arrayOf<MyMessage>(MyMessage("101"), MyMessage("102")))
                put("arrayP", arrayOf<TestModel>(TestModel("101"), TestModel("102"))) //暂不支持
            }
        })
    }
}