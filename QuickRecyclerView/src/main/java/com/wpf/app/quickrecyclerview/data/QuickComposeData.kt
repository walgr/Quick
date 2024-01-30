package com.wpf.app.quickrecyclerview.data

import androidx.compose.runtime.Composable
import androidx.compose.runtime.InternalComposeApi
import androidx.compose.ui.platform.ComposeView
import com.wpf.app.quickrecyclerview.QuickAdapter
import com.wpf.app.quickrecyclerview.holder.QuickViewHolder
import com.wpf.app.quickutil.bind.runOnContextWithSelf

/**
 * View是Compose的Item
 */
@InternalComposeApi
open class QuickComposeData<T>(
    @Transient open val onCompose: RunOnCompose<T>,
    isDealBinding: Boolean = false,      //是否处理DataBinding
    autoSet: Boolean = false,             //自动映射
    isSuspension: Boolean = false       //View是否悬浮置顶
) : QuickBindData(layoutViewInContext = runOnContextWithSelf { context, _ ->
    ComposeView(context)
}, isDealBinding = isDealBinding, autoSet = autoSet, isSuspension = isSuspension) {

    override fun onBindViewHolder(
        adapter: QuickAdapter,
        viewHolder: QuickViewHolder<QuickBindData>,
        position: Int
    ) {
        super.onBindViewHolder(adapter, viewHolder, position)
        if (viewHolder.itemView is ComposeView) {
            (viewHolder.itemView as ComposeView).setContent {
                onCompose.OnCompose(this@QuickComposeData as T)
            }
        }
    }
}

interface RunOnCompose<T> {
    @Composable
    fun OnCompose(self: T)
}

inline fun <T> runOnCompose(crossinline onCompose: @Composable (T) -> Unit) = object : RunOnCompose<T> {
    @Composable
    override fun OnCompose(self: T) {
        return onCompose(self)
    }
}