package com.wpf.app.quickbind

import android.app.Activity
import android.app.Dialog
import android.view.View
import androidx.annotation.UiThread
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.wpf.app.base.bind.Bind
import com.wpf.app.base.bind.NoBind
import com.wpf.app.base.bind.QuickBindI
import com.wpf.app.base.bind.QuickBindWrap.bindHistory
import com.wpf.app.base.bind.plugins.BasePlugin
import com.wpf.app.quick.annotations.bind.BindData2View
import com.wpf.app.quick.annotations.bind.BindView
import com.wpf.app.quick.annotations.bind.Databinder
import com.wpf.app.quick.annotations.bind.GroupView
import com.wpf.app.quickbind.annotations.AutoGet
import com.wpf.app.quickbind.annotations.BindFragment
import com.wpf.app.quickbind.annotations.BindFragment2
import com.wpf.app.quickbind.annotations.BindFragments
import com.wpf.app.quickbind.annotations.BindFragments2
import com.wpf.app.quickbind.annotations.BindSp2View
import com.wpf.app.quickbind.annotations.LoadSp
import com.wpf.app.quickbind.plugins.AutoGetPlugin
import com.wpf.app.quickbind.plugins.BindData2ViewPlugin
import com.wpf.app.quickbind.plugins.BindFragment2Plugin
import com.wpf.app.quickbind.plugins.BindFragmentPlugin
import com.wpf.app.quickbind.plugins.BindFragments2Plugin
import com.wpf.app.quickbind.plugins.BindFragmentsPlugin
import com.wpf.app.quickbind.plugins.BindSp2ViewPlugin
import com.wpf.app.quickbind.plugins.BindViewPlugin
import com.wpf.app.quickbind.plugins.GroupViewPlugin
import com.wpf.app.quickbind.plugins.LoadSpPlugin
import com.wpf.app.quickutil.helper.contentView
import com.wpf.app.quickutil.other.GenericEx.getFieldAndParent
import com.wpf.app.quickutil.other.forceTo
import java.lang.reflect.Constructor
import java.lang.reflect.Field
import java.lang.reflect.InvocationTargetException
import kotlin.reflect.KClass

/**
 * Created by 王朋飞 on 2022/7/13.
 *
 */
object QuickBind: QuickBindI {
    private var bindSpFileName = "QuickViewSpBindFile"

    private val plugins: LinkedHashMap<KClass<out Annotation>, BasePlugin> = LinkedHashMap()
    private val bindDataPlugin = mutableMapOf<KClass<out Annotation>, BasePlugin>(Pair(BindData2View::class, BindData2ViewPlugin()))

    override fun getRegisterPlugins(): MutableMap<KClass<out Annotation>, BasePlugin> {
        return plugins
    }

    override fun getBindPlugin(): MutableMap<KClass<out Annotation>, BasePlugin> {
        return bindDataPlugin
    }

    override fun <T : BasePlugin> registerPlugin(ann: KClass<out Annotation>, plugin: T) {
        plugins[ann] = plugin
    }

    override fun <T : BasePlugin> registerPlugin(index: Int, ann: KClass<out Annotation>, plugin: T) {
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
        registerPlugin(BindFragments2::class, BindFragments2Plugin())
        registerPlugin(BindFragment2::class, BindFragment2Plugin())
        registerPlugin(BindData2View::class, BindData2ViewPlugin())
    }

    override fun bind(activity: Activity) {
        super.bind(activity)
        if (activity is NoBind) return
        if (bindHistory.find { it.get() == activity } != null) return
        bind(activity, null)
    }

    override fun bind(activity: Activity, viewModel: ViewModel?) {
        super.bind(activity, viewModel)
        if (activity is NoBind) return
        if (bindHistory.find { it.get() == activity } != null) return
        bindBinder(viewModel ?: activity, activity.contentView())
        dealInPlugins(activity, viewModel)
    }

    override fun bind(fragment: Fragment) {
        super.bind(fragment)
        if (fragment is NoBind) return
        if (bindHistory.find { it.get() == fragment } != null) return
        bind(fragment, null)
    }

    override fun bind(fragment: Fragment, viewModel: ViewModel?) {
        super.bind(fragment, viewModel)
        if (fragment is NoBind) return
        if (bindHistory.find { it.get() == fragment } != null) return
        fragment.view?.let {
            bindBinder(viewModel ?: fragment, it)
        }
        dealInPlugins(fragment, viewModel)
    }

    override fun bind(viewHolder: RecyclerView.ViewHolder) {
        if (viewHolder is NoBind) return
        if (bindHistory.find { it.get() == viewHolder } != null) return
        bindBinder(viewHolder, viewHolder.itemView)
        dealInPlugins(viewHolder, null)
    }

    override fun bind(dialog: Dialog) {
        if (dialog is NoBind) return
        if (bindHistory.find { it.get() == dialog } != null) return
        bindBinder(dialog, dialog.window!!.decorView)
        dealInPlugins(dialog, null)
    }

    override fun bind(dialog: Dialog, viewModel: ViewModel?) {
        if (dialog is NoBind) return
        if (bindHistory.find { it.get() == dialog } != null) return
        bindBinder(dialog, dialog.window!!.decorView)
        dealInPlugins(dialog, viewModel)
    }

    override fun <T : Bind> bind(bind: T) {
        if (bindHistory.find { it.get() == bind } != null) return
        bind.getView()?.let {
            bindBinder(bind, it)
        }
        dealInPlugins(bind, null)
    }

    val bindMap: MutableMap<Class<*>, Databinder> = LinkedHashMap()
    private fun bindBinder(target: Any, source: View) {
        val targetClass: Class<*> = target.javaClass
        val constructor = findBindingConstructorForClass(targetClass) ?: return
        try {
            bindMap[targetClass] = constructor.newInstance(target, source) as Databinder
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

    private val bindingMap: MutableMap<Class<*>, Constructor<Databinder>?> = LinkedHashMap()

    @UiThread
    private fun findBindingConstructorForClass(cls: Class<*>): Constructor<Databinder>? {
        var bindingConstructor = bindingMap[cls]
        if (bindingConstructor != null || bindingMap.containsKey(cls)) {
            return bindingConstructor
        }
        val clsPackage = cls.getPackage()?.name
        val clsName = cls.name
        val clsSimpleName = cls.simpleName
        if (clsName.startsWith("android.") || clsName.startsWith("java.")
            || clsName.startsWith("androidx.")
        ) {
            return null
        }
        bindingConstructor = try {
            val bindingClass =
                cls.classLoader?.loadClass(clsPackage + ".Quick_" + clsSimpleName + "_ViewBinding_Ksp")
            bindingClass?.getConstructor(
                cls,
                View::class.java
            )?.forceTo<Constructor<Databinder>>()
        } catch (e: ClassNotFoundException) {
            findBindingConstructorForClass(cls.superclass as Class<*>)
        } catch (e: NoSuchMethodException) {
            throw RuntimeException("Unable to find binding constructor for $clsName", e)
        }
        bindingMap[cls] = bindingConstructor
        return bindingConstructor
    }

    override fun dealInPlugins(obj: Any?, viewModel: ViewModel?) {
        dealInPlugins(obj, viewModel, plugins)
    }

    override fun dealInPlugins(
        obj: Any?,
        viewModel: ViewModel?,
        plugins: MutableMap<KClass<out Annotation>, BasePlugin>
    ) {
        if (obj == null) return
        try {
            if (viewModel != null) {
                val viewModelFields: List<Field> = getFieldAndParent(viewModel)
                for (field in viewModelFields) {
                    val annotations = field.annotations.toMutableList()
                    annotations.sortBy { plugins.keys.indexOf(it.annotationClass) }
                    annotations.forEach {
                        plugins[it.annotationClass]?.dealField(obj, viewModel, field)
                    }
                    annotations.clear()
                }
            } else {
                val fields: List<Field> = getFieldAndParent(obj)
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

    @Suppress("unused")
    fun setBindSpFileName(bindSpFileName: String) {
        QuickBind.bindSpFileName = bindSpFileName
    }

    override fun getBindSpFileName(): String {
        return bindSpFileName
    }
}