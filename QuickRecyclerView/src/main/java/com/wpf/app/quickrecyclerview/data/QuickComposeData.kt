package com.wpf.app.quickrecyclerview.data

import android.view.View
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import com.wpf.app.quickutil.bind.runOnContextWithSelf

open class QuickComposeData<T>(
    @Transient open val onCompose: RunOnCompose<T>,
    @Transient override val isDealBinding: Boolean = false,      //是否处理DataBinding
    @Transient override val autoSet: Boolean = false,             //自动映射
    @Transient override val isSuspension: Boolean = false   //View是否悬浮置顶
) : QuickBindData(layoutViewInContext = runOnContextWithSelf { context, _ ->
    ComposeView(context)
}) {
    override fun onCreateViewHolder(itemView: View) {
        super.onCreateViewHolder(itemView)
        if (itemView is ComposeView) {
            itemView.setContent {
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