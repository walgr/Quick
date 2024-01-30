package com.wpf.app.quick.demo.model

import android.annotation.SuppressLint
import com.wpf.app.quick.demo.R
import com.wpf.app.quick.annotations.bind.BindData2View
import com.wpf.app.quick.demo.databinding.HolderImageBinding
import com.wpf.app.quickbind.helper.binddatahelper.Text2TextView
import com.wpf.app.quickbind.helper.binddatahelper.Url2ImageView
import com.wpf.app.quickrecyclerview.data.QuickAbilityData
import com.wpf.app.quickrecyclerview.data.binding
import com.wpf.app.quickrecyclerview.data.clickWSelf
import com.wpf.app.quickrecyclerview.data.with
import com.wpf.app.quickutil.bind.runOnHolder
import com.wpf.app.quickutil.init.ToastHelper
import com.wpf.app.quickutil.other.printLog

/**
 * Created by 王朋飞 on 2022/7/5.
 */
class BindDataTestModel : QuickAbilityData(R.layout.holder_image,
    abilityList =
    binding<HolderImageBinding> {
        tvTitle.text.printLog("当前View-")
    }.with(clickWSelf<BindDataTestModel> {
        ToastHelper.show("点击了:${getViewPos()}")
    })
) {
    @SuppressLint("NonConstantResourceId")
    @BindData2View(id = R.id.img, helper = Url2ImageView::class)
    var imgSrc =
        "https://img1.baidu.com/it/u=3009731526,373851691&fm=253&fmt=auto&app=138&f=JPEG?w=800&h=500"

    @SuppressLint("NonConstantResourceId")
    @BindData2View(id = R.id.tvTitle, helper = Text2TextView::class)
    val title = runOnHolder {
        "Title:${getViewPos()}"
    }
}