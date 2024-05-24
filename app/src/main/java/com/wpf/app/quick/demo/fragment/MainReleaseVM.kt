package com.wpf.app.quick.demo.fragment

import android.annotation.SuppressLint
import android.view.View
import android.widget.TextView
import androidx.core.os.bundleOf
import com.wpf.app.quick.activity.viewmodel.QuickVBModel
import com.wpf.app.quick.annotations.bind.BindView
import com.wpf.app.quick.demo.DialogTestActivity
import com.wpf.app.quick.demo.FragmentTestActivity
import com.wpf.app.quick.demo.IntentDataTestActivity
import com.wpf.app.quick.demo.R
import com.wpf.app.quick.demo.RecyclerViewTestActivity
import com.wpf.app.quick.demo.SelectListTestActivity
import com.wpf.app.quick.demo.ViewPagerNotifySizeTestActivity
import com.wpf.app.quick.demo.WheelViewTestActivity
import com.wpf.app.quick.demo.databinding.FragmentMainReleaseBinding
import com.wpf.app.quick.demo.model.MyMessage
import com.wpf.app.quick.demo.wanandroid.WanAndroidHomeActivity
import com.wpf.app.quick.helper.startActivity
import com.wpf.app.quickbind.annotations.BindSp2View
import com.wpf.app.quickrecyclerview.utils.QuickStickyHelper
import com.wpf.app.quickrecyclerview.utils.StickyItemDecoration
import com.wpf.app.quickutil.other.printLog

class MainReleaseVM: QuickVBModel<MainReleaseFragment, FragmentMainReleaseBinding>() {

    @SuppressLint("NonConstantResourceId", "StaticFieldLeak")
    @BindSp2View(bindSp = "绑定的SpKey3", defaultValue = "默认值3")
    @BindView(R.id.spTextView3)
    var text3: TextView? = null
    @SuppressLint("SetTextI18n")
    override fun onBindingCreated(view: FragmentMainReleaseBinding) {
        text3?.postDelayed(
            { text3?.text = System.currentTimeMillis().toString() + "" },
            1000
        )
        view.list.addItemDecoration(StickyItemDecoration(QuickStickyHelper()))
    }

    fun gotoWanAndroid(view: View?) {
        view?.context?.startActivity<WanAndroidHomeActivity> {
            it.resultCode.printLog("返回数据成功")
        }
    }

    fun gotoDialog(view: View?) {
        view?.context?.startActivity<DialogTestActivity>()
    }

    fun gotoWheelTest(view: View?) {
        view?.context?.startActivity<WheelViewTestActivity>()
    }

    fun gotoViewPagerNotifySize(view: View?) {
        view?.context?.startActivity<ViewPagerNotifySizeTestActivity>()
    }

    fun gotoSelectList(view: View?) {
        view?.context?.startActivity<SelectListTestActivity>()
    }

    fun gotoList(view: View?) {
        view?.context?.startActivity<RecyclerViewTestActivity>()
    }

    fun gotoFragmentActivity(view: View?) {
        view?.context?.startActivity<FragmentTestActivity>()
    }

    fun gotoData(view: View?) {
        view?.context?.startActivity<IntentDataTestActivity>(bundleOf(
            "activityTitle" to "数据测试页",
            "intD" to 2,
            "floatD" to 3f,
            "doubleD" to 4.0,
            "charD" to 'b',
            "byteD" to 6.toByte(),
            "data" to MyMessage("31"),
            "map" to object : HashMap<String?, String?>() {
                init {
                    put("map1", "51")
                }
            },
            "list" to object : ArrayList<String?>() {
                init {
                    add("61")
                    add("62")
                }
            },
            "array" to arrayOf("71", "72"),
            "listS" to object : ArrayList<MyMessage?>() {
                init {
                    add(MyMessage("81"))
                    add(MyMessage("82"))
                }
            },
            "arrayS" to arrayOf(MyMessage("101"), MyMessage("102"))
        ))
    }
}