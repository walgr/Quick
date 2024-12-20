package com.wpf.app.quick.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.wpf.app.quickutil.Quick
import com.wpf.app.quickutil.bind.Bind
import com.wpf.app.quickutil.bind.BindBaseFragment
import com.wpf.app.quickutil.bind.QuickBindWrap
import com.wpf.app.quickutil.helper.InitViewHelper
import com.wpf.app.quickutil.network.RequestCoroutineScope
import kotlinx.coroutines.Job

/**
 * Created by 王朋飞 on 2022/7/13.
 *
 */
open class QuickBaseFragment @JvmOverloads constructor(
    @LayoutRes val layoutId: Int = 0,
    val layoutView: View? = null,
    val layoutViewCreate: (Context.() -> View)? = null,
    val layoutViewCreateWithQuick: (Context.(self: Quick?) -> View)? = null,
) : Fragment(), BindBaseFragment, RequestCoroutineScope, Quick, Bind {

    override var jobManager: MutableList<Job> = mutableListOf()

    private var mView: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerForActivityResult()
    }

    var launcher: ActivityResultLauncher<Intent>? = null
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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        onCreateWithSavedInstanceState(savedInstanceState)
        if (mView == null) {
            mView = generateContentView(
                InitViewHelper.init(
                    inflater.context,
                    layoutId,
                    layoutView,
                    layoutViewCreate,
                    self = this,
                    layoutViewCreateWithQuick = layoutViewCreateWithQuick
                )
            )
        }
        return mView
    }

    open fun onCreateWithSavedInstanceState(savedInstanceState: Bundle?) {

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        QuickBindWrap.bind(this)
        initView(mView!!)
    }

    open fun initView(view: View) {

    }

    open fun generateContentView(view: View): View {
        return view
    }

    override fun getView(): View? {
        return mView
    }

    @CallSuper
    override fun onDestroy() {
        super.onDestroy()
        cancelJob()
        mView = null
        launcher = null
    }
}