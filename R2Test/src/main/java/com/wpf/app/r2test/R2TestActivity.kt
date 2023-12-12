package com.wpf.app.r2test

import android.annotation.SuppressLint
import android.widget.TextView
import com.wpf.app.quick.activity.QuickActivity
import com.wpf.app.quick.annotations.bind.BindData2View
import com.wpf.app.quick.annotations.bind.BindView
import com.wpf.app.quick.annotations.bind.GroupView
import com.wpf.app.quickbind.helper.binddatahelper.Text2TextView
import com.wpf.app.quickbind.utils.GroupViews

/**
 * Created by 王朋飞 on 2022/6/16.
 */
class R2TestActivity : QuickActivity(R.layout.test_layout, titleName = "测试R2") {

    @JvmField
    @SuppressLint("NonConstantResourceId")
    @BindView(R2.id.info)
    var info1: TextView? = null

    @JvmField
    @GroupView(idList = [R2.id.recyclerView, R2.id.info])
    var mGroupViews: GroupViews? = null

    @JvmField
    @SuppressLint("NonConstantResourceId")
    @BindData2View(id = R2.id.info, helper = Text2TextView::class)
    var titleStr: CharSequence = "测试R2成功"

    @SuppressLint("SetTextI18n")
    override fun initView() {
        info1?.postDelayed({ mGroupViews?.goneAll() }, 1000)
    }
}