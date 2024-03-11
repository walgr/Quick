package com.wpf.app.quick.demo

import android.annotation.SuppressLint
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.wpf.app.quick.ability.QuickAbilityActivity
import com.wpf.app.quick.ability.modelBinding
import com.wpf.app.quick.ability.viewModel
import com.wpf.app.quick.ability.with
import com.wpf.app.quick.activity.QuickVBActivity
import com.wpf.app.quick.annotations.bind.BindView
import com.wpf.app.quick.annotations.bind.GroupView
import com.wpf.app.quick.demo.databinding.ActivityDataTestBinding
import com.wpf.app.quick.demo.viewmodel.IntentDataTestModel
import com.wpf.app.quickbind.utils.GroupViews
import com.wpf.app.quickutil.helper.postDelay
import com.wpf.app.quickwork.activity.contentWithTitle


/**
 * Created by 王朋飞 on 2022/6/13.
 */
class IntentDataTestActivity : QuickAbilityActivity(
    contentWithTitle(R.layout.activity_data_test, titleName = "传输测试页")
        .with(modelBinding<IntentDataTestModel, ActivityDataTestBinding>())
) {
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.title)
    var title: TextView? = null

    @GroupView(idList = [R.id.title, R.id.title1])
    var textGroup: GroupViews? = null

    @SuppressLint("SetTextI18n")
    override fun initView(view: View) {
        super.initView(view)
        title?.text = "传输测试页Activity"
        title?.postDelay(1000) {
            textGroup?.goneAll()
            Toast.makeText(this, "传输数据成功", Toast.LENGTH_SHORT).show()
        }
    }
}