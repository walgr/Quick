package com.wpf.app.quick.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.wpf.app.quickbind.QuickBind
import com.wpf.app.quickbind.annotations.AutoGet
import com.wpf.app.quickbind.interfaces.BindBaseFragment
import com.wpf.app.quickutil.bind.RunOnContext
import com.wpf.app.quicknetwork.base.RequestCoroutineScope
import com.wpf.app.quickutil.bind.Bind
import kotlinx.coroutines.Job

/**
 * Created by 王朋飞 on 2022/7/13.
 *
 */
@SuppressLint("ValidFragment")
abstract class QuickFragment @JvmOverloads constructor(
    @LayoutRes open val layoutId: Int = 0,
    open val layoutView: View? = null,
    open val layoutViewInContext: RunOnContext<View>? = null,
    @AutoGet(titleKey) open val titleName: String = ""
) : Fragment(), BindBaseFragment, QuickView, RequestCoroutineScope, Bind {

    override var jobManager: MutableList<Job> = mutableListOf()

    private var mView: View? = null

    init {
        val bundle = Bundle()
        bundle.putString(titleKey, this.titleName)
        arguments = bundle
    }

    abstract fun initView(view: View?)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (mView == null) {
            if (layoutId != 0) {
                mView = inflater.inflate(layoutId, null)
            } else if (layoutView != null) {
                mView = layoutView
            } else if (layoutViewInContext != null && container?.context != null) {
                mView = layoutViewInContext?.run(container.context!!)
            }
        }
        return mView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewCreated(view)
    }

    private fun viewCreated(view: View?) {
        QuickBind.bind(this)
        initView(view)
    }

    override fun getView(): View? {
        return mView
    }

    override fun onDestroy() {
        super.onDestroy()
        cancelJob()
    }

    companion object {
        const val titleKey = "title"
    }
}