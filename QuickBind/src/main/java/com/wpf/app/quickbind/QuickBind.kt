package com.wpf.app.quickbind

import android.app.Activity
import android.app.Dialog
import android.view.View
import androidx.annotation.UiThread
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.wpf.app.quick.runtime.Databinder
import com.wpf.app.quickbind.plugins.*
import com.wpf.app.quickbind.utils.ReflectHelper
import java.lang.reflect.Constructor
import java.lang.reflect.Field
import java.lang.reflect.InvocationTargetException

/**
 * Created by 王朋飞 on 2022/7/13.
 *
 */
object QuickBind {
    private var bindSpFileName = "QuickViewSpBindFile"

    private val plugins: MutableList<BasePlugin> = mutableListOf()
    val bindPlugin: MutableList<BasePlugin> = mutableListOf(BindData2ViewPlugin())

    fun <T : BasePlugin> registerPlugin(plugin: T) {
        plugins.add(plugin)
    }

    init {
        //顺序不能乱
        registerPlugin(BindViewPlugin())
        registerPlugin(GroupViewPlugin())
        registerPlugin(AutoGetPlugin())
        registerPlugin(BindSp2ViewPlugin())
        registerPlugin(LoadSpPlugin())
        registerPlugin(BindFragmentsPlugin())
        registerPlugin(BindFragmentPlugin())
        registerPlugin(BindData2ViewPlugin())
    }

    fun bind(activity: Activity) {
        bind(activity, null)
    }

    fun bind(activity: Activity, viewModel: ViewModel?) {
        bindBinder(activity, activity.window.decorView)
        dealInPlugins(activity, viewModel)
    }

    fun bind(fragment: Fragment) {
        bind(fragment, null)
    }

    fun bind(fragment: Fragment, viewModel: ViewModel?) {
        fragment.view?.let { bindBinder(fragment, it) }
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

    fun dealInPlugins(obj: Any?, viewModel: ViewModel?, plugins: MutableList<BasePlugin>) {
        if (obj == null) return
        try {
            val fields: List<Field> = ReflectHelper.getFieldWithParent(obj)
            for (field in fields) {
                for (plugin in plugins) {
                    val result = plugin.dealField(obj, null, field)
                    if (result) {
                        break
                    }
                }
            }
            if (viewModel != null) {
                val viewModelFields: List<Field> = ReflectHelper.getFieldWithParent(viewModel)
                for (field in viewModelFields) {
                    for (plugin in plugins) {
                        val result = plugin.dealField(obj, viewModel, field)
                        if (result) {
                            break
                        }
                    }
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