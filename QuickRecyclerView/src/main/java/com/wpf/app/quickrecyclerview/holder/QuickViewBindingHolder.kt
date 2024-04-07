package com.wpf.app.quickrecyclerview.holder

import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.wpf.app.quickutil.run.RunOnContextWithSelf
import com.wpf.app.quickrecyclerview.QuickAdapter
import com.wpf.app.quickrecyclerview.constant.BRConstant
import com.wpf.app.quickrecyclerview.data.QuickItemData

/**
 * Created by 王朋飞 on 2022/7/13.
 *
 */
open class QuickViewBindingHolder<T: QuickItemData, VB : ViewDataBinding> @JvmOverloads constructor(
    mParent: ViewGroup,
    @LayoutRes layoutId: Int = 0,
    layoutViewInContext: RunOnContextWithSelf<ViewGroup, View>? = null,
    dealBindView: Boolean = false,
    autoClick: Boolean = false,
) : QuickViewHolder<T>(mParent, layoutId, layoutViewInContext, dealBindView, autoClick) {

    internal var mViewBinding: VB? = null

    open var variableBinding: Map<Int, Any>? = null

    override fun onCreateViewHolder(itemView: View) {
        super.onCreateViewHolder(itemView)
        if (mViewBinding == null) {
            mViewBinding = DataBindingUtil.bind(itemView)
        }
    }

    @CallSuper
    override fun onBindViewHolder(adapter: QuickAdapter, data: T?, position: Int) {
        super.onBindViewHolder(adapter, data, position)
        if (mViewBinding != null) {
            mViewBinding!!.setVariable(BRConstant.data, data)
            mViewBinding!!.setVariable(BRConstant.adapter, adapter)
            mViewBinding!!.setVariable(BRConstant.position, position)
            if (variableBinding != null) {
                val kes: Set<Int> = variableBinding!!.keys
                for (key in kes) {
                    mViewBinding!!.setVariable(key, variableBinding!![key])
                }
            }
            mViewBinding!!.executePendingBindings()
        }
    }

    fun getViewBinding(): VB? = mViewBinding
}