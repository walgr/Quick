package com.wpf.app.quick.base.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import com.wpf.app.quick.base.helper.QuickBindHelper
import java.io.Serializable

/**
 * Created by 王朋飞 on 2022/4/2.
 *  layoutId、layoutView 必须有1个，同时存在时layoutView有效
 */
abstract class BaseActivity(
    @LayoutRes private val layoutId: Int? = null,
    private val layoutView: View? = null,
    open val activityTitle: String = "",
) : AppCompatActivity(), BaseView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dealContentView()
        QuickBindHelper.bind(this)
        initView()
        setTitle()
    }

    override fun onResume() {
        super.onResume()
        setTitle()
    }

    override fun initView() {

    }

    fun setTitle() {
        if (activityTitle.isNotEmpty()) {
            supportActionBar?.title = activityTitle
        }
    }

    open fun dealContentView() {
        layoutId?.let { setContentView(layoutId) }
            ?: let { layoutView?.let { setContentView(layoutView) } }
    }

    fun <T : Activity> startActivity(
        activityCls: Class<T>,
        data: Map<String, Any?>? = null
    ) {
        startActivity(Intent(this, activityCls).also { intent ->
            data?.forEach {
                when (it.value) {
                    is Serializable -> {
                        intent.putExtra(it.key, it.value as Serializable)
                    }
                    is Parcelable -> {
                        intent.putExtra(it.key, it.value as Parcelable)
                    }
                    is Array<*> -> {
                        intent.putExtra(it.key, it.value as Array<*>)
                    }
                    is ArrayList<*> -> {
                        intent.putParcelableArrayListExtra(
                            it.key,
                            it.value as ArrayList<out Parcelable>
                        )
                    }
                }
            }
        })
    }
}