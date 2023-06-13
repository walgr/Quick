package com.wpf.app.quick.demo

import android.annotation.SuppressLint
import android.widget.TextView
import android.widget.Toast
import com.wpf.app.quick.activity.QuickVBActivity
import com.wpf.app.quick.annotations.BindView
import com.wpf.app.quick.annotations.GroupView
import com.wpf.app.quick.demo.databinding.ActivityDataTestBinding
import com.wpf.app.quick.demo.viewmodel.IntentDataTestModel
import com.wpf.app.quickbind.utils.GroupViews


/**
 * Created by 王朋飞 on 2022/6/13.
 */
class IntentDataTestActivity :
    QuickVBActivity<IntentDataTestModel, ActivityDataTestBinding>(
        R.layout.activity_data_test,
        titleName = "传输测试页"
    ) {
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.title)
    var title: TextView? = null

    @GroupView(idList = [R.id.title, R.id.title1])
    var textGroup: GroupViews? = null

    @SuppressLint("SetTextI18n")
    override fun initView(viewDataBinding: ActivityDataTestBinding?) {
        title?.text = "传输测试页Activity"
        title?.postDelayed({
            textGroup?.goneAll()
            Toast.makeText(this, "传输数据成功", Toast.LENGTH_SHORT).show()
        }, 1000)
    }
}