package com.wpf.app.quickutil.bind

import android.app.Activity
import android.app.Dialog
import androidx.annotation.CallSuper
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.wpf.app.quickutil.init.QuickInit
import com.wpf.app.quickutil.bind.plugins.BasePlugin
import kotlin.reflect.KClass

interface QuickBindI {

    @CallSuper
    fun bind(activity: Activity) {
        QuickInit.init(activity.applicationContext)
    }

    @CallSuper
    fun bind(activity: Activity, viewModel: ViewModel?) {
        QuickInit.init(activity.applicationContext)
    }

    @CallSuper
    fun bind(fragment: Fragment) {
        QuickInit.init(fragment.requireActivity().applicationContext)
    }

    @CallSuper
    fun bind(fragment: Fragment, viewModel: ViewModel?) {
        QuickInit.init(fragment.requireActivity().applicationContext)
    }

    fun bind(viewHolder: RecyclerView.ViewHolder)

    fun bind(dialog: Dialog)

    fun <T : Bind> bind(bind: T)

    fun getRegisterPlugins(): MutableMap<KClass<out Annotation>, BasePlugin>?

    fun getBindPlugin(): MutableMap<KClass<out Annotation>, BasePlugin>

    fun <T : BasePlugin> registerPlugin(ann: KClass<out Annotation>, plugin: T)

    fun <T : BasePlugin> registerPlugin(index: Int, ann: KClass<out Annotation>, plugin: T)

    fun dealInPlugins(obj: Any?, viewModel: ViewModel?)

    fun dealInPlugins(
        obj: Any?,
        viewModel: ViewModel?,
        plugins: MutableMap<KClass<out Annotation>, BasePlugin>
    )

    fun getBindSpFileName(): String
}