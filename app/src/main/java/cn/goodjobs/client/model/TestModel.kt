package cn.goodjobs.client.model

import cn.goodjobs.client.adapterholder.TestHolder
import com.wpf.app.base.widgets.recyclerview.CommonItemData
import com.wpf.app.base.widgets.recyclerview.HolderClass

/**
 * Created by 王朋飞 on 2022/5/11.
 *
 */
@HolderClass(TestHolder::class)
class TestModel(val text: String = "1") : CommonItemData() {

}