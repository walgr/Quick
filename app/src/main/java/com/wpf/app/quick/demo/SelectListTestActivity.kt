package com.wpf.app.quick.demo

import android.annotation.SuppressLint
import android.view.View
import androidx.lifecycle.MutableLiveData
import com.wpf.app.quick.activity.QuickViewModelBindingActivity
import com.wpf.app.quick.annotations.BindData2View
import com.wpf.app.quick.annotations.BindView
import com.wpf.app.quick.demo.databinding.ActivitySelectTestBinding
import com.wpf.app.quick.demo.model.ParentSelectItem
import com.wpf.app.quick.helper.binddatahelper.ItemClick
import com.wpf.app.quick.demo.model.SelectItem
import com.wpf.app.quick.demo.viewmodel.SelectListViewModel
import com.wpf.app.quick.utils.LogUtil
import com.wpf.app.quick.widgets.recyclerview.data.QuickChildSelectData
import com.wpf.app.quick.widgets.recyclerview.listeners.OnSelectCallback
import com.wpf.app.quick.widgets.selectview.QuickMultistageSelectView
import com.wpf.app.quickbind.interfaces.itemClick

/**
 * Created by 王朋飞 on 2022/7/8.
 */
class SelectListTestActivity :
    QuickViewModelBindingActivity<SelectListViewModel, ActivitySelectTestBinding>(R.layout.activity_select_test, titleName = "选择筛选页") {

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.selectList)
    var selectListView: QuickMultistageSelectView? = null

    @SuppressLint("NonConstantResourceId")
    @BindData2View(id = R.id.btnClean, helper = ItemClick::class)
    var btnClean = itemClick {
        clean(it)
    }

    fun clean(view: View?) {
//        list2?.cleanAll()
    }
}