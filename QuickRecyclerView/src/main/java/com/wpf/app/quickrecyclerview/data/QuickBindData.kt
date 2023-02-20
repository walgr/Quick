package com.wpf.app.quickrecyclerview.data

import android.content.Context
import android.view.View
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.wpf.app.quickbind.QuickBind
import com.wpf.app.quickbind.interfaces.RunOnContext
import com.wpf.app.quickrecyclerview.QuickAdapter
import com.wpf.app.quickrecyclerview.constant.BRConstant
import com.wpf.app.quickrecyclerview.holder.QuickViewHolder
import com.wpf.app.quickutil.bind.Bind

/**
 * Created by 王朋飞 on 2022/7/13.
 *
 */
open class QuickBindData @JvmOverloads constructor(
    @LayoutRes open val layoutId: Int = 0,
    @Transient open val layoutView: View? = null,
    @Transient open val layoutViewInContext: RunOnContext<View>? = null,
    isSuspension: Boolean = false,                      //View是否悬浮置顶
    val isDealBinding: Boolean = false,                 //是否处理DataBinding
) : QuickItemData(isSuspension = isSuspension), Bind {

    @Transient
    private var mViewHolder: QuickViewHolder<QuickBindData>? = null

    private var variableBinding: Map<Int, Any>? = null
    var mViewBinding: ViewDataBinding? = null

    @Transient
    private var mAdapter: QuickAdapter? = null

    @Transient
    private var mView: View? = null

    @Transient
    private var isFirstLoad = true

    @CallSuper
    open fun onCreateViewHolder(itemView: View) {
        this.mView = itemView
        QuickBind.bind(this)
        if (isDealBinding) {
            mViewBinding = DataBindingUtil.bind(itemView)
        }
    }

    open fun getContext(): Context? {
        return mView?.context
    }

    @CallSuper
    open fun onBindViewHolder(
        adapter: QuickAdapter,
        viewHolder: QuickViewHolder<QuickBindData>,
        position: Int
    ) {
        mAdapter = adapter
        mViewHolder = viewHolder
        mView = viewHolder.itemView
        if (isFirstLoad) {
            onCreateViewHolder(itemView = viewHolder.itemView)
        } else {
            QuickBind.dealInPlugins(this, null, QuickBind.bindDataPlugin)
        }
        if (isDealBinding) {
            if (mViewBinding != null) {
                mViewBinding!!.setVariable(BRConstant.data, this)
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
        isFirstLoad = false
    }

    open fun getAdapter(): QuickAdapter? {
        return mAdapter
    }

    open fun getViewHolder(): QuickViewHolder<QuickBindData>? {
        return mViewHolder
    }

    open fun getDataPos(): Int {
        return getAdapter()?.getData()?.indexOf(this) ?: 0
    }

    open fun getViewPos(): Int {
        return getViewHolder()?.bindingAdapterPosition ?: 0
    }

    override fun getView(): View? {
        return mView
    }
}