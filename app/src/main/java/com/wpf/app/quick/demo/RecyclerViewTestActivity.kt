package com.wpf.app.quick.demo

import android.widget.LinearLayout
import com.wpf.app.quick.ability.QuickActivity
import com.wpf.app.quick.ability.ex.contentView
import com.wpf.app.quick.ability.ex.modelBinding
import com.wpf.app.quick.ability.ex.myLayout
import com.wpf.app.quick.ability.ex.with
import com.wpf.app.quick.demo.databinding.ActivityRecyclerviewTestBinding
import com.wpf.app.quick.demo.model.BindDataTestModel
import com.wpf.app.quick.demo.viewmodel.RecyclerViewTestModel
import com.wpf.app.quickwork.ability.title

/**
 * Created by 王朋飞 on 2022/6/13.
 */
class RecyclerViewTestActivity : QuickActivity(contentView<LinearLayout> {
    title("列表测试页")
    myLayout(R.layout.activity_recyclerview_test)
}.with(modelBinding<RecyclerViewTestModel, ActivityRecyclerviewTestBinding> {
    list.setData(
        mutableListOf(
            BindDataTestModel(0),
            BindDataTestModel(1),
            BindDataTestModel(2),
            BindDataTestModel(3),
            BindDataTestModel(4),
            BindDataTestModel(5),
            BindDataTestModel(6),
            BindDataTestModel(7),
            BindDataTestModel(8),
            BindDataTestModel(9),
            BindDataTestModel(10),
            BindDataTestModel(11),
            BindDataTestModel(12),
            BindDataTestModel(13),
            BindDataTestModel(14),
            BindDataTestModel(15),
            BindDataTestModel(16),
            BindDataTestModel(17),
            BindDataTestModel(18),
            BindDataTestModel(19),
            BindDataTestModel(20),
        )
    )
})
)