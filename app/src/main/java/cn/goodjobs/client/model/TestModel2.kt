package cn.goodjobs.client.model

import android.annotation.SuppressLint
import androidx.databinding.Bindable
import androidx.lifecycle.MutableLiveData
import cn.goodjobs.client.BR
import cn.goodjobs.client.R
import cn.goodjobs.client.databinding.HolderTest2Binding
import com.google.gson.Gson
import com.wpf.app.base.widgets.recyclerview.CommonItemDataBinding
import com.wpf.app.base.widgets.recyclerview.HolderBindingLayout
import kotlin.random.Random

/**
 * Created by 王朋飞 on 2022/5/11.
 *
 */
@SuppressLint("NonConstantResourceId")
@HolderBindingLayout(R.layout.holder_test2)
class TestModel2(
    var select1: MutableLiveData<Boolean> = MutableLiveData(false),
    var title: MutableLiveData<String>
) : CommonItemDataBinding<HolderTest2Binding>() {

//    @Bindable
    var select2: Boolean  = false
        set(value) {
            field = value
//            notifyPropertyChanged(BR.select2)
        }
    init {
        val int = Random.nextInt(100).toString()
        title.postValue(int)
    }

    override fun toString(): String {
        return Gson().toJson(this)
    }

}