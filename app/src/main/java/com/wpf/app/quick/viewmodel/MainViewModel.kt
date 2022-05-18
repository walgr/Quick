package com.wpf.app.quick.viewmodel

import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import com.wpf.app.quick.base.constant.BRConstant
import com.wpf.app.quick.base.viewmodel.BindingViewModel
import com.wpf.app.quick.base.widgets.recyclerview.CommonAdapterListener
import com.wpf.app.quick.base.widgets.recyclerview.CommonItemData
import com.wpf.app.quick.databinding.ActivityMainBinding
import com.wpf.app.quick.model.TestModel2
import com.wpf.app.quick.model.TestModel3

/**
 * Created by 王朋飞 on 2022/5/10.
 *
 */
class MainViewModel : BindingViewModel<ActivityMainBinding>() {

    val data = MutableLiveData<List<CommonItemData>>()
    val select = MutableLiveData<List<String>>()

    override fun onModelCreate() {
        super.onModelCreate()
        val testModel = TestModel2().also {
            it.id = "-1"
            it.title = "-1"
        }
        viewBinding?.setVariable(BRConstant.data, testModel)

        val listData = arrayListOf<CommonItemData>()
        listData.add(testModel)
        for (i in 1..25) {
            val testModel2 = TestModel2().also {
                it.id = i.toString()
                it.title = i.toString()
            }
            listData.add(testModel2)
        }
        for (i in 26..50) {
            val testModel2 = TestModel3(text = MutableLiveData((i).toString())).also {
                it.id = i.toString()
            }
            listData.add(testModel2)
        }
        data.postValue(listData)
        select.postValue(arrayListOf("2"))
        viewBinding?.list?.mAdapter?.commonAdapterListener = object : CommonAdapterListener<CommonItemData> {
            override fun onItemClick(view: View, data: CommonItemData?, position: Int) {
                Log.e("点击", "" + position)
            }
        }
        viewBinding?.list?.postDelayed({
            viewBinding?.list?.mAdapter?.getData()?.forEachIndexed {index, it ->
                (it as? TestModel2)?.title=(index.toString())
            }
            (viewBinding?.list?.mAdapter?.getData()?.getOrNull(0) as? TestModel2)?.isSelect?.postValue(true)
            (viewBinding?.list?.mAdapter?.getData()?.getOrNull(0) as? TestModel2)?.select2 = true
        }, 3000)
    }

}