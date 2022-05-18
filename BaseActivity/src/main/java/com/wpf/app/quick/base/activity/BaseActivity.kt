package com.wpf.app.quick.base.activity

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity

/**
 * Created by 王朋飞 on 2022/4/2.
 *  layoutId、layoutView 必须有1个，同时存在时layoutView有效
 */
abstract class BaseActivity(
    @LayoutRes private val layoutId: Int? = null,
    private val layoutView: View? = null,
) : AppCompatActivity(), BaseView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dealContentView()
        initView()
    }

    open fun dealContentView() {
        layoutId?.let { setContentView(layoutId) }
            ?: let { layoutView?.let { setContentView(layoutView) } }
    }
}