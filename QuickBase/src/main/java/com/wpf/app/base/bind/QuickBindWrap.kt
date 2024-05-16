package com.wpf.app.base.bind

import android.app.Activity
import android.app.Dialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.wpf.app.base.bind.plugins.BasePlugin
import java.lang.ref.SoftReference
import kotlin.reflect.KClass

object QuickBindWrap: QuickBindI {
    val bindHistory = mutableListOf<SoftReference<Any>>()
    private var quickBindI: QuickBindI? = null
    init {
        runCatching {
            quickBindI = Class.forName("com.wpf.app.quickbind.QuickBind").getField("INSTANCE").get(null) as QuickBindI
        }
    }

    override fun bind(activity: Activity) {
        super.bind(activity)
        if (activity is NoBind) return
        if (bindHistory.find { it.get() == activity } != null) return
        quickBindI?.bind(activity)
    }

    override fun bind(activity: Activity, viewModel: ViewModel?) {
        super.bind(activity, viewModel)
        if (activity is NoBind) return
        if (bindHistory.find { it.get() == activity } != null) return
        quickBindI?.bind(activity, viewModel)
    }

    override fun bind(fragment: Fragment) {
        super.bind(fragment)
        if (fragment is NoBind) return
        if (bindHistory.find { it.get() == fragment } != null) return
        quickBindI?.bind(fragment)
    }

    override fun bind(fragment: Fragment, viewModel: ViewModel?) {
        super.bind(fragment, viewModel)
        if (fragment is NoBind) return
        if (bindHistory.find { it.get() == fragment } != null) return
        quickBindI?.bind(fragment, viewModel)
    }

    override fun bind(viewHolder: RecyclerView.ViewHolder) {
        if (viewHolder is NoBind) return
        if (bindHistory.find { it.get() == viewHolder } != null) return
        quickBindI?.bind(viewHolder)
    }

    override fun bind(dialog: Dialog) {
        if (dialog is NoBind) return
        if (bindHistory.find { it.get() == dialog } != null) return
        quickBindI?.bind(dialog)
    }

    override fun bind(dialog: Dialog, viewModel: ViewModel?) {
        if (dialog is NoBind) return
        if (bindHistory.find { it.get() == dialog } != null) return
        quickBindI?.bind(dialog, viewModel)
    }

    override fun <T : Bind> bind(bind: T) {
        if (bindHistory.find { it.get() == bind } != null) return
        quickBindI?.bind(bind)
    }

    fun bind(any: Any) {
        when (any) {
            is Activity -> {
                bind(any)
            }

            is Fragment -> {
                bind(any)
            }

            is Dialog -> {
                bind(any)
            }

            is RecyclerView.ViewHolder -> {
                bind(any)
            }
        }
    }

    override fun getRegisterPlugins(): MutableMap<KClass<out Annotation>, BasePlugin>? {
        return quickBindI?.getRegisterPlugins()
    }

    override fun getBindPlugin(): MutableMap<KClass<out Annotation>, BasePlugin> {
        return quickBindI!!.getBindPlugin()
    }

    override fun <T : BasePlugin> registerPlugin(ann: KClass<out Annotation>, plugin: T) {
        quickBindI?.registerPlugin(ann, plugin)
    }

    override fun <T : BasePlugin> registerPlugin(index: Int, ann: KClass<out Annotation>, plugin: T) {
        quickBindI?.registerPlugin(index, ann, plugin)
    }

    override fun dealInPlugins(obj: Any?, viewModel: ViewModel?) {
        quickBindI?.dealInPlugins(obj, viewModel)
    }

    override fun dealInPlugins(
        obj: Any?,
        viewModel: ViewModel?,
        plugins: MutableMap<KClass<out Annotation>, BasePlugin>
    ) {
        quickBindI?.dealInPlugins(obj, viewModel, plugins)
    }

    override fun getBindSpFileName(): String {
        return quickBindI!!.getBindSpFileName()
    }
}