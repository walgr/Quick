package com.wpf.app.quick.demo.fragment

import android.annotation.SuppressLint
import android.app.Activity
import android.view.View
import android.widget.TextView
import com.wpf.app.quick.activity.viewmodel.QuickVBModel
import com.wpf.app.quick.annotations.bind.BindView
import com.wpf.app.quick.demo.DialogTestActivity
import com.wpf.app.quick.demo.IntentDataTestActivity
import com.wpf.app.quick.demo.R
import com.wpf.app.quick.demo.RecyclerViewTestActivity
import com.wpf.app.quick.demo.RefreshListTestActivity
import com.wpf.app.quick.demo.SelectListTestActivity
import com.wpf.app.quick.demo.ViewPagerBindFragmentTestActivity
import com.wpf.app.quick.demo.databinding.FragmentMainReleaseBinding
import com.wpf.app.quick.demo.model.MyMessage
import com.wpf.app.quick.demo.wanandroid.WanAndroidHomeActivity
import com.wpf.app.quickbind.annotations.BindSp2View
import com.wpf.app.quickutil.activity.quickStartActivity

class MainReleaseVM: QuickVBModel<FragmentMainReleaseBinding>() {

    @SuppressLint("NonConstantResourceId", "StaticFieldLeak")
    @BindSp2View(bindSp = "绑定的SpKey2", defaultValue = "默认值2")
    @BindView(R.id.spTextView2)
    var text2: TextView? = null
    override fun onBindingCreated(view: FragmentMainReleaseBinding?) {

    }

    fun gotoWanAndroid(view: View?) {
        view?.context?.quickStartActivity<WanAndroidHomeActivity>()
    }

    fun gotoDialog(view: View?) {
        view?.context?.quickStartActivity<DialogTestActivity>()
    }

    fun gotoR2Test(view: View?) {
        try {
            view?.context?.quickStartActivity(activityCls = Class.forName("com.wpf.app.r2test.R2TestActivity") as Class<Activity>)
        } catch (ignore: Exception) { }
    }

    fun gotoGlide(view: View?) {
        view?.context?.quickStartActivity<ViewPagerBindFragmentTestActivity>()
    }

    fun gotoRefreshList(view: View?) {
        view?.context?.quickStartActivity<RefreshListTestActivity>()
    }

    fun gotoSelectList(view: View?) {
        view?.context?.quickStartActivity<SelectListTestActivity>()
    }

    fun gotoList(view: View?) {
        view?.context?.quickStartActivity<RecyclerViewTestActivity>()
    }

    fun gotoData(view: View?) {
        view?.context?.quickStartActivity<IntentDataTestActivity>(object : HashMap<String, Any?>() {
            init {
                put("activityTitle", "数据测试页")
                put("intD", 2)
                put("floatD", 3f)
                put("doubleD", 4.0)
                put("charD", 'b')
                put("byteD", 6.toByte())
                put("data", MyMessage("31"))
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
                put("arrayS", arrayOf(MyMessage("101"), MyMessage("102")))
            }
        })
    }
}