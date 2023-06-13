package com.wpf.app.quick.demo.http.model

import com.google.gson.Gson
import com.wpf.app.quickbind.interfaces.runOnContextWithSelf
import com.wpf.app.quickrecyclerview.data.QuickItemData
import com.wpf.app.quickrecyclerview.data.QuickRequestList

class 首页文章 : QuickRequestList() {
    val curPage: Int? = null
    val datas: List<Article>? = null

//    @SuppressLint("NonConstantResourceId")
//    @BindData2View(id = R.id.info, helper = Text2TextView::class)
    //自动赋值处理了 可以不用写上面的了
    val info = runOnContextWithSelf<String, 首页文章> { _, self ->
        "请求成功：" + self.curPage + "---" + Gson().toJson(self.datas)
    }

    override fun returnList(): List<QuickItemData>? {
        return datas
    }
}