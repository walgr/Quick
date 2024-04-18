package com.wpf.app.quick.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.ActivityResultRegistry
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import com.wpf.app.base.QuickView
import com.wpf.app.base.bind.Bind
import com.wpf.app.base.bind.QuickBindWrap
import com.wpf.app.quicknetwork.base.RequestCoroutineScope
import com.wpf.app.quickutil.helper.InitViewHelper
import kotlinx.coroutines.Job

/**
 * Created by 王朋飞 on 2022/7/13.
 *
 */
open class QuickBaseActivity @JvmOverloads constructor(
    @LayoutRes open val layoutId: Int = 0,
    open val layoutView: View? = null,
    open val layoutViewCreate: (Context.() -> View)? = null,
) : AppCompatActivity(), QuickView, RequestCoroutineScope, Bind {

    override var jobManager: MutableList<Job> = mutableListOf()

    private var mView: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mView = generateContentView(
            InitViewHelper.init(
                this,
                layoutId,
                layoutView,
                layoutViewCreate
            )
        )
        setContentView(mView)
        QuickBindWrap.bind(this)
        initView(mView!!)
        registerForActivityResult()
    }

    open fun initView(view: View) {

    }

    var launcher: ActivityResultLauncher<Intent>? = null

    private var resultRegister: ActivityResultRegistry? = null
    private var resultCallback: ActivityResultCallback<ActivityResult>? = null
    open fun registerForActivityResult(
        resultCallback: ActivityResultCallback<ActivityResult>,
    ) {
        this.resultCallback = resultCallback
    }

    private fun registerForActivityResult() {
        launcher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            resultCallback?.onActivityResult(result)
        }
    }

    open fun generateContentView(view: View): View {
        return view
    }

    override fun getView(): View {
        return mView!!
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
        mView = null
        launcher = null
    }
}