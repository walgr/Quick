package com.wpf.app.quick.demo.model

import android.annotation.SuppressLint
import android.widget.FrameLayout
import com.wpf.app.base.ability.base.with
import com.wpf.app.quick.ability.ex.contentView
import com.wpf.app.quick.ability.helper.myLayout
import com.wpf.app.quick.ability.helper.roundRect
import com.wpf.app.quick.annotations.bind.BindData2View
import com.wpf.app.quick.demo.R
import com.wpf.app.quick.demo.databinding.DragItemBinding
import com.wpf.app.quick.demo.databinding.HolderImageBinding
import com.wpf.app.quickbind.helper.binddatahelper.Text2TextView
import com.wpf.app.quickbind.helper.binddatahelper.Url2ImageView
import com.wpf.app.quickrecyclerview.ability.bindSwipeMenu
import com.wpf.app.quickrecyclerview.ability.bindWSelf
import com.wpf.app.quickrecyclerview.ability.click
import com.wpf.app.quickrecyclerview.ability.swap
import com.wpf.app.quickrecyclerview.data.QuickAbilityData
import com.wpf.app.quickutil.helper.dpF
import com.wpf.app.quickutil.helper.onceClick
import com.wpf.app.quickutil.helper.toDrawable
import com.wpf.app.quickutil.init.ToastHelper
import com.wpf.app.quickutil.other.printLog
import com.wpf.app.quickutil.run.runOnView

/**
 * Created by 王朋飞 on 2022/7/5.
 */
class BindDataTestModel(private val index: Int = 0) : QuickAbilityData(
    abilityList = contentView<FrameLayout> {
        roundRect(background = R.color.white.toDrawable(), radius = 8.dpF()) {
            myLayout(R.layout.holder_image)
        }
    }.with(bindWSelf<HolderImageBinding, BindDataTestModel> {
        tvTitle.text.printLog("当前View-", "index:${it.index}")
    }).with(click<BindDataTestModel> {
        ToastHelper.show("点击了位置:${this.index}")
    }).with(bindSwipeMenu<DragItemBinding, BindDataTestModel>(
        R.layout.drag_item,
        canSwipe = { this.index != 2 }) { self, swipeLayout ->
        btnCopy.onceClick {
            swipeLayout.smoothClose()
            ToastHelper.show("复制完成：${self.index}")
        }
        btnDel.onceClick {
            swipeLayout.smoothClose()
            ToastHelper.show("删除完成：${self.index}")
        }
    }).with(swap<BindDataTestModel> { this.index != 3 })
) {
    @SuppressLint("NonConstantResourceId")
    @BindData2View(id = R.id.img, helper = Url2ImageView::class)
    var imgSrc =
        "https://img1.baidu.com/it/u=3009731526,373851691&fm=253&fmt=auto&app=138&f=JPEG?w=800&h=500"

    @SuppressLint("NonConstantResourceId")
    @BindData2View(id = R.id.tvTitle, helper = Text2TextView::class)
    val title = runOnView {
        "Title:${index}"
    }
}