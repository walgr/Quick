package com.wpf.app.quick.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import com.wpf.app.quickbind.QuickBind
import com.wpf.app.quickbind.annotations.AutoGet
import com.wpf.app.quickbind.interfaces.BindBaseFragment

/**
 * Created by 王朋飞 on 2022/7/13.
 *
 */
/**
 * Created by 王朋飞 on 2022/6/15.
 */
@SuppressLint("ValidFragment")
abstract class QuickFragment @JvmOverloads constructor(
    @LayoutRes open val layoutId: Int = 0,
    open val layoutView: View? = null,
    @AutoGet(titleKey) open val titleName: String = ""
) : BindBaseFragment(), QuickView {

    init {
        val bundle = Bundle()
        bundle.putString(titleKey, titleName)
        arguments = bundle
    }

    abstract fun initView(view: View?)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return if (layoutId != 0) {
            inflater.inflate(layoutId, null)
        } else layoutView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewCreated(view)
    }

    private fun viewCreated(view: View?) {
        QuickBind.bind(this)
        initView(view)
    }

    companion object {
        const val titleKey = "title"
    }
}