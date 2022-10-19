package com.wpf.app.quickrecyclerview.holder

import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.wpf.app.quickrecyclerview.QuickAdapter
import com.wpf.app.quickrecyclerview.constant.BRConstant
import com.wpf.app.quickrecyclerview.data.QuickViewDataBinding

/**
 * Created by 王朋飞 on 2022/7/13.
 *
 */
open class QuickViewBindingHolder<T : QuickViewDataBinding<VB>, VB : ViewDataBinding>
@JvmOverloads constructor(
    override val mParent: ViewGroup,
    @LayoutRes override var layoutId: Int = 0,
    override var layoutView: View? = null,
    override var dealBindView: Boolean = false,
) : QuickViewHolder<T>(mParent, layoutId = layoutId, layoutView = layoutView, dealBindView = dealBindView) {

    var mViewData: T? = null
    var mViewBinding: VB? = null

    protected var variableBinding: Map<Int, Any>? = null

    override fun onCreateViewHolder(itemView: View) {
        super.onCreateViewHolder(itemView)
        mViewBinding = DataBindingUtil.bind(itemView)
        mViewData!!.onHolderCreated(this)
        onCreateHolderEnd(itemView)
    }

    open fun onCreateHolderEnd(itemView: View) {}

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
}