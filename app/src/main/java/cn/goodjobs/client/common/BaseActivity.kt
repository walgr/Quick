package cn.goodjobs.client.common

import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.annotation.LayoutRes

/**
 * Created by 王朋飞 on 2022/4/2.
 *  layoutId、layoutView 必须有1个，同时存在时layoutView有效
 */
abstract class BaseActivity<T: BaseViewModel<out BaseView>>(
    @LayoutRes private val layoutId: Int? = null,
    private val layoutView: View? = null,
    private val viewModel: T? = null
) : ComponentActivity(), BaseView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dealContentView()
        initView()
    }

    private fun dealContentView() {
        layoutId?.let { setContentView(layoutId) }
            ?: let { layoutView?.let { setContentView(layoutView) } }
    }

}