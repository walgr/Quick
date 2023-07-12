package com.wpf.app.quick.activity

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import com.wpf.app.quick.annotations.BindView
import com.wpf.app.quickbind.QuickBind
import com.wpf.app.quickbind.annotations.AutoGet
import com.wpf.app.quickbind.interfaces.RunOnContext
import com.wpf.app.quicknetwork.base.RequestCoroutineScope
import com.wpf.app.quickutil.bind.Bind
import kotlinx.coroutines.Job

/**
 * Created by 王朋飞 on 2022/7/13.
 *
 */
abstract class QuickActivity @JvmOverloads constructor(
    @LayoutRes open val layoutId: Int = 0,
    open val layoutView: View? = null,
    open val layoutViewInContext: RunOnContext<View>? = null,
    @AutoGet(QuickFragment.titleKey) open val titleName: String = ""
) : AppCompatActivity(), QuickView, RequestCoroutineScope, Bind {

    override var jobManager: MutableList<Job> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dealContentView()
        QuickBind.bind(this)
        initView()
        setTitleName()
    }

    abstract fun initView()

    open fun setTitleName() {
        if (titleName.isNotEmpty()) {
            supportActionBar?.title = titleName
        }
    }

    protected open fun dealContentView() {
        if (layoutId != 0) {
            setContentView(layoutId)
        } else if (layoutView != null) {
            setContentView(layoutView)
        } else if (layoutViewInContext != null) {
            setContentView(layoutViewInContext?.run(this))
        }
    }

    override fun getView(): View? {
        return window.decorView
    }

    override fun onDestroy() {
        super.onDestroy()
        cancelJob()
    }
}