package com.wpf.app.quickbind

import android.app.Activity
import android.app.Dialog
import android.util.Log
import android.view.View
import androidx.annotation.UiThread
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.wpf.app.quick.annotations.BindData2View
import com.wpf.app.quick.annotations.BindView
import com.wpf.app.quick.annotations.GroupView
import com.wpf.app.quick.runtime.Databinder
import com.wpf.app.quickbind.annotations.*
import com.wpf.app.quickbind.interfaces.Bind
import com.wpf.app.quickbind.plugins.*
import com.wpf.app.quickbind.utils.ReflectHelper
import java.lang.reflect.Constructor
import java.lang.reflect.Field
import java.lang.reflect.InvocationTargetException
import kotlin.reflect.KClass

/**
 * Created by 王朋飞 on 2022/7/13.
 *
 */
object QuickBind {
    private var bindSpFileName = "QuickViewSpBindFile"

    private val plugins: MutableMap<KClass<out Annotation>, BasePlugin> = LinkedHashMap()
    val bindPlugin: MutableMap<KClass<out Annotation>, BasePlugin> =
        linkedMapOf(Pair(BindData2View::class, BindData2ViewPlugin()))

    fun <T : BasePlugin> registerPlugin(ann: KClass<out Annotation>, plugin: T) {
        plugins[ann] = plugin
    }

    init {
        //顺序不能乱
        registerPlugin(BindView::class, BindViewPlugin())
        registerPlugin(GroupView::class, GroupViewPlugin())
        registerPlugin(AutoGet::class, AutoGetPlugin())
        registerPlugin(BindSp2View::class, BindSp2ViewPlugin())
        registerPlugin(LoadSp::class, LoadSpPlugin())
        registerPlugin(BindFragments::class, BindFragmentsPlugin())
        registerPlugin(BindFragment::class, BindFragmentPlugin())
        registerPlugin(BindData2View::class, BindData2ViewPlugin())
    }

    fun bind(activity: Activity) {
        bind(activity, null)
    }

    fun bind(activity: Activity, viewModel: ViewModel?) {
        bindBinder(viewModel ?: activity, activity.window.decorView)
        dealInPlugins(activity, viewModel)
    }

    fun bind(fragment: Fragment) {
        bind(fragment, null)
    }

    fun bind(fragment: Fragment, viewModel: ViewModel?) {
        fragment.view?.let {
            bindBinder(viewModel ?: fragment, it)
        }
        dealInPlugins(fragment, viewModel)
    }

    fun bind(viewHolder: RecyclerView.ViewHolder) {
        bindBinder(viewHolder, viewHolder.itemView)
        dealInPlugins(viewHolder, null)
    }

    fun bind(dialog: Dialog) {
        bindBinder(dialog, dialog.window!!.decorView)
        dealInPlugins(dialog, null)
    }

    fun <T : Bind> bind(bind: T) {
        bindBinder(bind, bind.getView())
        dealInPlugins(bind, null)
    }

    val BINDEDMAP: MutableMap<Class<*>, Databinder> = LinkedHashMap()
    private fun bindBinder(target: Any, source: View) {
        val targetClass: Class<*> = target.javaClass
        val constructor = findBindingConstructorForClass(targetClass) ?: return
        try {
            BINDEDMAP[targetClass] = constructor.newInstance(target, source) as Databinder
        } catch (e: IllegalAccessException) {
            throw RuntimeException("Unable to invoke $constructor", e)
        } catch (e: InstantiationException) {
            throw RuntimeException("Unable to invoke $constructor", e)
        } catch (e: InvocationTargetException) {
            val cause = e.cause
            if (cause is RuntimeException) {
                throw (cause as RuntimeException?)!!
            }
            if (cause is Error) {
                throw (cause as Error?)!!
            }
            throw RuntimeException("Unable to create binding instance.", cause)
        }
    }

    val BINDINGS: MutableMap<Class<*>, Constructor<out Databinder>?> = LinkedHashMap()

    @UiThread
    private fun findBindingConstructorForClass(cls: Class<*>): Constructor<out Databinder>? {
        var bindingCtor = BINDINGS[cls]
        if (bindingCtor != null || BINDINGS.containsKey(cls)) {
            return bindingCtor
        }
        val clsPackage = cls.getPackage()?.name
        val clsName = cls.name
        val clsSimpleName = cls.simpleName
        if (clsName.startsWith("android.") || clsName.startsWith("java.")
            || clsName.startsWith("androidx.")
        ) {
            return null
        }
        bindingCtor = try {
            val bindingClass =
                cls.classLoader?.loadClass(clsPackage + ".Quick_" + clsSimpleName + "_ViewBinding")
            bindingClass?.getConstructor(
                cls,
                View::class.java
            ) as Constructor<out Databinder>
        } catch (e: ClassNotFoundException) {
            findBindingConstructorForClass(cls.superclass)
        } catch (e: NoSuchMethodException) {
            throw RuntimeException("Unable to find binding constructor for $clsName", e)
        }
        BINDINGS[cls] = bindingCtor
        return bindingCtor
    }

    fun dealInPlugins(obj: Any?, viewModel: ViewModel?) {
        dealInPlugins(obj, viewModel, plugins)
    }

    fun dealInPlugins(
        obj: Any?,
        viewModel: ViewModel?,
        plugins: MutableMap<KClass<out Annotation>, BasePlugin>
    ) {
        if (obj == null) return
        try {
            if (viewModel != null) {
                val viewModelFields: List<Field> = ReflectHelper.getFieldWithParent(viewModel)
                for (field in viewModelFields) {
                    val annotations = field.annotations.toMutableList()
                    annotations.sortBy { plugins.keys.indexOf(it.annotationClass) }
                    annotations.forEach {
                        plugins[it.annotationClass]?.dealField(obj, viewModel, field)
                    }
                    annotations.clear()
                }
            } else {
                val fields: List<Field> = ReflectHelper.getFieldWithParent(obj)
                for (field in fields) {
                    val annotations = field.annotations.toMutableList()
                    annotations.sortBy { plugins.keys.indexOf(it.annotationClass) }
                    annotations.forEach {
                        plugins[it.annotationClass]?.dealField(obj, null, field)
                    }
                    annotations.clear()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun setBindSpFileName(bindSpFileName: String) {
        QuickBind.bindSpFileName = bindSpFileName
    }

    fun getBindSpFileName(): String {
        return bindSpFileName
    }
}