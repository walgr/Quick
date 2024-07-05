package com.wpf.app.quick.demo

import android.widget.LinearLayout
import com.wpf.app.quickutil.ability.base.with
import com.wpf.app.quickutil.ability.ex.contentView
import com.wpf.app.quickutil.ability.helper.viewGroupCreate
import com.wpf.app.quick.ability.QuickActivity
import com.wpf.app.quick.ability.ex.modelBinding
import com.wpf.app.quick.demo.databinding.ActivitySelectTestBinding
import com.wpf.app.quick.demo.viewmodel.SelectListModel
import com.wpf.app.quickwork.ability.helper.title

/**
 * Created by 王朋飞 on 2022/7/8.
 */
class SelectListTestActivity : QuickActivity(
    contentView<LinearLayout> {
        title("选择筛选页")
        viewGroupCreate(R.layout.activity_select_test)
    }.with(modelBinding<SelectListModel, ActivitySelectTestBinding>())
)