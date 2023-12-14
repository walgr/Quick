package com.wpf.app.quick.demo

import com.wpf.app.quick.activity.QuickListVBActivity
import com.wpf.app.quick.activity.QuickVBActivity
import com.wpf.app.quick.demo.databinding.ActivityRecyclerviewTestBinding
import com.wpf.app.quick.demo.model.BindDataTestModel
import com.wpf.app.quick.demo.viewmodel.RecyclerViewTestModel
import com.wpf.app.quickrecyclerview.data.and
import com.wpf.app.quickrecyclerview.utils.LineItem
import com.wpf.app.quickutil.other.dp2px
import com.wpf.app.quickutil.other.toColor
import java.security.AccessController.getContext

/**
 * Created by 王朋飞 on 2022/6/13.
 */
class RecyclerViewTestActivity :
    QuickListVBActivity<RecyclerViewTestModel, ActivityRecyclerviewTestBinding>(
        dataList = listOf(
            BindDataTestModel(),
            BindDataTestModel(),
            BindDataTestModel(),
        ),
        layoutId = R.layout.activity_recyclerview_test,
        listId = R.id.list,
        titleName = "列表测试页",
        getVMFormActivity = true
    )