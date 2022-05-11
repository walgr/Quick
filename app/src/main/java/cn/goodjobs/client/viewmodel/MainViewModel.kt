package cn.goodjobs.client.viewmodel

import androidx.lifecycle.MutableLiveData
import cn.goodjobs.client.databinding.ActivityMainBinding
import com.wpf.app.base.activity.BindingViewModel

/**
 * Created by 王朋飞 on 2022/5/10.
 *
 */
class MainViewModel : BindingViewModel<ActivityMainBinding>() {
    var showGlide = MutableLiveData(false)

    override fun onModelCreate() {
        super.onModelCreate()
        viewBinding?.glideView?.postDelayed({
            showGlide.postValue(true)
//            viewBinding?.glideView?.visibility = View.VISIBLE
        }, 1000)
    }
}