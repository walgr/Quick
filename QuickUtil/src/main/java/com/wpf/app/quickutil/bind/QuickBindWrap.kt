package com.wpf.app.quickutil.bind

import android.app.Activity
import android.app.Dialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.wpf.app.quickutil.bind.plugins.BasePlugin
import kotlin.reflect.KClass

object QuickBindWrap: QuickBindI {
    private var quickBindI: QuickBindI? = null
    init {
        try {
            quickBindI = Class.forName("com.wpf.app.quickbind.QuickBind").getField("INSTANCE").get(null) as QuickBindI
        } catch (e : Exception) {
            e.printStackTrace()
        }
    }

    override fun bind(activity: Activity) {
        super.bind(activity)
        quickBindI?.bind(activity)
    }

    override fun bind(activity: Activity, viewModel: ViewModel?) {
        super.bind(activity, viewModel)
        quickBindI?.bind(activity, viewModel)
    }

    override fun bind(fragment: Fragment) {
        super.bind(fragment)
        quickBindI?.bind(fragment)
    }

    override fun bind(fragment: Fragment, viewModel: ViewModel?) {
        super.bind(fragment, viewModel)
        quickBindI?.bind(fragment, viewModel)
    }

    override fun bind(viewHolder: RecyclerView.ViewHolder) {
        quickBindI?.bind(viewHolder)
    }

    override fun bind(dialog: Dialog) {
        quickBindI?.bind(dialog)
    }

    override fun <T : Bind> bind(bind: T) {
        quickBindI?.bind(bind)
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