package com.wpf.app.quickrecyclerview.data

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.wpf.app.quickbind.QuickBind
import com.wpf.app.quickutil.bind.RunOnContextWithSelf
import com.wpf.app.quickbind.utils.DataAutoSet2ViewUtils
import com.wpf.app.quickrecyclerview.QuickAdapter
import com.wpf.app.quickrecyclerview.constant.BRConstant
import com.wpf.app.quickrecyclerview.holder.QuickViewHolder
import com.wpf.app.quickutil.bind.Bind
import java.io.Serializable

/**
 * Created by 王朋飞 on 2022/7/13.
 * 绑定数据到xml的Item
 */
open class QuickBindData @JvmOverloads constructor(
    @Transient @LayoutRes override val layoutId: Int = 0,
    @Transient override val layoutViewInContext: RunOnContextWithSelf<ViewGroup, View>? = null,
    @Transient open val isDealBinding: Boolean = false,      //是否处理DataBinding
    @Transient open val autoSet: Boolean = false,             //自动映射
    @Transient override val isSuspension: Boolean = false   //View是否悬浮置顶
) : QuickViewData(), Bind, Serializable {

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
        if (autoSet) {
            mView?.let {
                DataAutoSet2ViewUtils.autoSet(this, mView!!)
            }
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