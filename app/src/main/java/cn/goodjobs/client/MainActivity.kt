package cn.goodjobs.client

import android.util.Log
import android.view.View
import android.widget.CheckBox
import androidx.lifecycle.MutableLiveData
import cn.goodjobs.client.model.TestModel2
import com.wpf.app.base.activity.ViewModelBindingActivity
import cn.goodjobs.client.viewmodel.MainViewModel
import com.wpf.app.base.widgets.recyclerview.CommonAdapterListener
import com.wpf.app.base.widgets.recyclerview.CommonRecyclerView

class MainActivity : ViewModelBindingActivity<MainViewModel>(R.layout.activity_main) {
    private var list: CommonRecyclerView? = null

    override fun initView() {
        list = findViewById(R.id.list)
        val listData = arrayListOf<TestModel2>()
        for (i in 0..50) {
            val testModel2 = TestModel2(title = MutableLiveData(i.toString()))
            listData.add(testModel2)
        }
        list?.mAdapter?.commonAdapterListener = object : CommonAdapterListener<TestModel2> {
            override fun onItemClick(view: View, data: TestModel2, position: Int) {
                Log.e("点击", "" + position + "" + data.select1.value)
                if (view.id == com.wpf.app.base.R.id.checkbox) {
//                    data.select1.postValue((view as CheckBox).isChecked)
                }

            }
        }
        list?.mAdapter?.setNewData(listData)

        list?.postDelayed({
            (list?.mAdapter?.getData()?.get(0) as TestModel2).select1.value = true
            Log.e("点击", "" + (list?.mAdapter?.getData()?.get(0) as TestModel2).select2)
        }, 3000)
    }

}