package com.wpf.app.quick.activity

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import com.wpf.app.quickbind.QuickBind
import com.wpf.app.quickbind.annotations.AutoGet
import com.wpf.app.quickutil.bind.RunOnContext
import com.wpf.app.quicknetwork.base.RequestCoroutineScope
import com.wpf.app.quickutil.activity.contentView
import com.wpf.app.quickutil.activity.myContentView
import com.wpf.app.quickutil.bind.Bind
import com.wpf.app.quickutil.helper.InitViewHelper
import kotlinx.coroutines.Job

/**
 * Created by 王朋飞 on 2022/7/13.
 *
 */
abstract class QuickActivity @JvmOverloads constructor(
    @LayoutRes open val layoutId: Int = 0,
    open val layoutView: View? = null,
    open val layoutViewInContext: RunOnContext<View>? = null,
    @AutoGet(QuickFragment.TITLE_KEY) open val titleName: String = ""
) : AppCompatActivity(), QuickView, RequestCoroutineScope, Bind {

    override var jobManager: MutableList<Job> = mutableListOf()

    open fun dealBind() = true

    private var inflateView: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inflateView = InitViewHelper.init(this, layoutId, layoutView, layoutViewInContext)
        dealContentView(inflateView!!)
        if (dealBind()) {
            QuickBind.bind(this)
        }
        initView(getView())
        setTitleName()
    }

    abstract fun initView(view: View)

    open fun setTitleName() {
        if (titleName.isNotEmpty()) {
            supportActionBar?.title = titleName
        }
    }

    protected open fun dealContentView(view: View) {
        setContentView(view)
    }

    override fun getView(): View {
        return myContentView()
    }

    fun requireContext() = this

    fun onBackPress(doNext: () -> Unit) {
        onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                doNext.invoke()
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        cancelJob()
    }
}