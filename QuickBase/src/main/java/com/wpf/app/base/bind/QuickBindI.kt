package com.wpf.app.base.bind

import android.app.Activity
import android.app.Dialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.wpf.app.base.bind.plugins.BasePlugin
import kotlin.reflect.KClass

interface QuickBindI {

    fun bind(activity: Activity) {

    }

    fun bind(activity: Activity, viewModel: ViewModel?) {

    }

    fun bind(fragment: Fragment) {

    }

    fun bind(fragment: Fragment, viewModel: ViewModel?) {

    }

    fun bind(dialog: Dialog) {

    }

    fun bind(dialog: Dialog, viewModel: ViewModel?) {

    }

    fun bind(viewHolder: RecyclerView.ViewHolder)

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