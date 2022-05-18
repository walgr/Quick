package com.wpf.app.quick

import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import com.wpf.app.quick.model.TestModel2
import com.wpf.app.quick.base.activity.ViewModelBindingActivity
import com.wpf.app.quick.viewmodel.MainViewModel
import com.wpf.app.quick.base.constant.BRConstant
import com.wpf.app.quick.base.widgets.recyclerview.CommonAdapterListener
import com.wpf.app.quick.base.widgets.recyclerview.CommonItemData
import com.wpf.app.quick.base.widgets.recyclerview.CommonRecyclerView
import com.wpf.app.quick.model.TestModel3

class MainActivity : ViewModelBindingActivity<MainViewModel>(R.layout.activity_main) {
    private var list: CommonRecyclerView? = null

    override fun initView() {
        val testModel = TestModel2().also {
            it.title = "-1"
        }
        viewModel?.viewBinding?.setVariable(BRConstant.data, testModel)
        list = findViewById(R.id.list)
        val listData = arrayListOf<CommonItemData>()
        listData.add(testModel)
        for (i in 1..25) {
            val testModel2 = TestModel2().also {
                it.title = i.toString()
            }
            listData.add(testModel2)
        }
        for (i in 26..50) {
            val testModel2 = TestModel3(text = MutableLiveData((i).toString()))
            listData.add(testModel2)
        }
        list?.mAdapter?.commonAdapterListener = object : CommonAdapterListener<CommonItemData> {
            override fun onItemClick(view: View, data: CommonItemData?, position: Int) {
                Log.e("点击", "" + position)
                if (view.id == com.wpf.app.quick.base.R.id.checkbox) {
//                    data.select1.postValue((view as CheckBox).isChecked)
                }

            }
        }
        list?.mAdapter?.setNewData(listData)
        list?.postDelayed({
            list?.mAdapter?.getData()?.forEachIndexed {index, it ->
                (it as? TestModel2)?.title=(index.toString())
            }
            (list?.mAdapter?.getData()?.get(0) as? TestModel2)?.select1?.postValue(true)
            (list?.mAdapter?.getData()?.get(0) as? TestModel2)?.select2 = true
        }, 3000)
    }

}