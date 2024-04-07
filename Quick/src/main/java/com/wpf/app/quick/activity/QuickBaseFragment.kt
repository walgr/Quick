package com.wpf.app.quick.activity

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
import com.wpf.app.base.QuickView
import com.wpf.app.quickbind.QuickBind
import com.wpf.app.quickbind.annotations.AutoGet
import com.wpf.app.quickbind.interfaces.BindBaseFragment
import com.wpf.app.quickutil.run.RunOnContext
import com.wpf.app.quicknetwork.base.RequestCoroutineScope
import com.wpf.app.quickutil.helper.InitViewHelper
import kotlinx.coroutines.Job

/**
 * Created by 王朋飞 on 2022/7/13.
 *
 */
abstract class QuickBaseFragment @JvmOverloads constructor(
    @LayoutRes open val layoutId: Int = 0,
    open val layoutView: View? = null,
    open val layoutViewInContext: RunOnContext<View>? = null,
    @AutoGet(TITLE_KEY) val titleName: String = "",
) : Fragment(), BindBaseFragment, QuickView, RequestCoroutineScope, com.wpf.app.base.bind.Bind {

    override var jobManager: MutableList<Job> = mutableListOf()

    private var curView: View? = null

    init {
        val bundle = Bundle()
        bundle.putString(TITLE_KEY, this.titleName)
        arguments = bundle
    }

    abstract fun initView(view: View)

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
        if (curView == null) {
            curView = generateContentView(InitViewHelper.init(inflater.context, layoutId, layoutView, layoutViewInContext))
        }
        return curView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        QuickBind.bind(this)
        initView(curView!!)
    }

    open fun generateContentView(view: View): View {
        return view
    }

    override fun getView(): View? {
        return curView
    }

    @CallSuper
    override fun onDestroy() {
        super.onDestroy()
        cancelJob()
        curView = null
        launcher = null
    }

    companion object {
        const val TITLE_KEY = "title"
    }
}