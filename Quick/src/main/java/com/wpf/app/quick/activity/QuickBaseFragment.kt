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
import androidx.activity.result.ActivityResultRegistry
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.wpf.app.base.Quick
import com.wpf.app.base.bind.Bind
import com.wpf.app.base.bind.QuickBindWrap
import com.wpf.app.quickbind.interfaces.BindBaseFragment
import com.wpf.app.quicknetwork.base.RequestCoroutineScope
import com.wpf.app.quickutil.helper.InitViewHelper
import kotlinx.coroutines.Job

/**
 * Created by 王朋飞 on 2022/7/13.
 *
 */
open class QuickBaseFragment @JvmOverloads constructor(
    @LayoutRes open val layoutId: Int = 0,
    open val layoutView: View? = null,
    open val layoutViewCreate: (Context.() -> View)? = null,
) : Fragment(), BindBaseFragment, RequestCoroutineScope, Quick, Bind {

    override var jobManager: MutableList<Job> = mutableListOf()

    private var mView: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerForActivityResult()
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
            ActivityResultContracts.StartActivityForResult()) { result ->
            resultCallback?.onActivityResult(result)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        if (mView == null) {
            mView = generateContentView(InitViewHelper.init(inflater.context, layoutId, layoutView, layoutViewCreate))
        }
        return mView
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