package com.wpf.app.quick.demo.wanandroid.model

import com.google.gson.Gson
import com.wpf.app.quickrecyclerview.data.QuickItemData
import com.wpf.app.quickrecyclerview.data.QuickRequestList
import com.wpf.app.quickutil.run.runOnContextWithSelf
import java.io.Serializable

class HomePage : QuickRequestList(), Serializable {
    val curPage: Int? = null
    val datas: List<Article>? = null

//    @SuppressLint("NonConstantResourceId")
//    @BindData2View(id = R.id.info, helper = Text2TextView::class)
    //自动赋值处理了 可以不用写上面的了
    val info = runOnContextWithSelf<HomePage, String> {
        "请求成功：" + curPage + "---" + Gson().toJson(datas)
    }

    override fun returnList(): List<QuickItemData>? {
        return datas
    }
}