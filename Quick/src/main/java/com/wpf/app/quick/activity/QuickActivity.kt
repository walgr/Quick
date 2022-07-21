package com.wpf.app.quick.activity

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import com.wpf.app.quickbind.QuickBind
import com.wpf.app.quickbind.annotations.AutoGet

/**
 * Created by 王朋飞 on 2022/7/13.
 *
 */
abstract class QuickActivity @JvmOverloads constructor(
    @LayoutRes open val layoutId: Int = 0,
    open val layoutView: View? = null,
    @AutoGet(QuickFragment.titleKey) open val titleName: String = ""
) : AppCompatActivity(), QuickView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dealContentView()
        QuickBind.bind(this)
        initView()
        setTitleName()
    }

    abstract override fun initView()

    private fun setTitleName() {
        if (titleName.isNotEmpty()) {
            supportActionBar?.title = titleName
        }
    }

    protected open fun dealContentView() {
        if (layoutId != 0) {
            setContentView(layoutId)
        } else layoutView?.let { setContentView(it) }
    }
}