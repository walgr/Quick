package com.wpf.app.quickrecyclerview.data

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import com.wpf.app.quickutil.bind.runOnContextWithSelf

open class QuickComposeData(
    @Transient open val onCompose: RunOnCompose
) : QuickBindData(layoutViewInContext = runOnContextWithSelf { context, _ ->
    ComposeView(context).apply {
        setContent(onCompose.run())
    }
})

interface RunOnCompose {
    fun run(): @Composable () -> Unit
}

fun runOnCompose(run: () -> (@Composable () -> Unit)) = object : RunOnCompose {
    override fun run(): @Composable () -> Unit {
        return run()
    }

}