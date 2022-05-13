package cn.goodjobs.client.model

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import cn.goodjobs.client.adapterholder.TestHolder3
import cn.goodjobs.client.databinding.HolderTest3Binding
import com.wpf.app.base.widgets.recyclerview.CommonItemDataBinding
import com.wpf.app.base.widgets.recyclerview.HolderBindingClass

/**
 * Created by 王朋飞 on 2022/5/11.
 *
 */
@SuppressLint("NonConstantResourceId")
@HolderBindingClass(TestHolder3::class)
class TestModel3(var text: MutableLiveData<String>): CommonItemDataBinding<HolderTest3Binding>() {

}