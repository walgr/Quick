package com.wpf.app.quick.ability

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.annotation.CallSuper
import androidx.annotation.StyleRes
import androidx.lifecycle.ViewModel
import com.wpf.app.base.ability.base.QuickAbility
import com.wpf.app.base.ability.base.QuickGenerateViewAbility
import com.wpf.app.base.ability.base.QuickInflateViewAbility
import com.wpf.app.base.ability.base.QuickLifecycleAbility
import com.wpf.app.base.ability.base.QuickInitViewAbility
import com.wpf.app.quickbind.interfaces.BindViewModel
import com.wpf.app.quickdialog.QuickBaseDialog
import com.wpf.app.quickutil.helper.InitViewHelper
import com.wpf.app.quickutil.other.asTo
import com.wpf.app.quickutil.other.forceTo

open class QuickDialog(
    context: Context,
    @StyleRes themeId: Int = 0,
    val abilityList: MutableList<QuickAbility> = mutableListOf(),
) : QuickBaseDialog(context = context, themeId = themeId, layoutViewCreate = {
    val inflateAbility = abilityList.first { ability -> ability is QuickInflateViewAbility }
        .forceTo<QuickInflateViewAbility>()
    InitViewHelper.init(
        this,
        inflateAbility.layoutId(),
        inflateAbility.layoutView(),
        inflateAbility.layoutViewCreate()
    )
}), BindViewModel<ViewModel> {
    val extraParameter: LinkedHashMap<String, Any> = linkedMapOf()

    companion object {
        var commonAbility: List<QuickAbility>? = null
    }

    init {
        commonAbility?.let {
            abilityList.addAll(0, it)
        }
    }

    override fun getViewModel(): ViewModel? {
        return abilityList.find { it is BindViewModel<*> }?.asTo<BindViewModel<*>>()?.getViewModel()
    }

    @CallSuper
    override fun generateContentView(view: View): View {
        var newView = view
        abilityList.filterIsInstance<QuickGenerateViewAbility>().forEach {
            newView = it.generateContentView(this, newView)
        }
        return newView
    }

    @CallSuper
    override fun initView(view: View) {
        abilityList.filterIsInstance<QuickGenerateViewAbility>().forEach {
            it.afterGenerateContentView(this, view)
        }
        super.initView(view)
        abilityList.filterIsInstance<QuickInitViewAbility>().forEach {
            it.initView(this, view)
        }
    }

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        abilityList.filterIsInstance<QuickLifecycleAbility>().forEach {
            it.onCreate()
        }
    }

    @CallSuper
    override fun onStart() {
        super.onStart()
        abilityList.filterIsInstance<QuickLifecycleAbility>().forEach {
            it.onStart()
        }
    }

    @CallSuper
    override fun onStop() {
        super.onStop()
        abilityList.filterIsInstance<QuickLifecycleAbility>().forEach {
            it.onStop()
        }
    }

    @CallSuper
    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        abilityList.filterIsInstance<QuickLifecycleAbility>().forEach {
            it.onDestroy()
        }
    }
}