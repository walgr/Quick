package com.wpf.app.quick.ability

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import androidx.annotation.CallSuper
import androidx.annotation.StyleRes
import androidx.lifecycle.ViewModel
import com.wpf.app.base.ability.base.QuickAbility
import com.wpf.app.base.ability.base.QuickGenerateViewAbility
import com.wpf.app.base.ability.base.QuickInflateViewAbility
import com.wpf.app.base.ability.base.QuickInitViewAbility
import com.wpf.app.base.ability.base.QuickLifecycleAbility
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
    val inflateAbility =
        abilityList.firstOrNull { ability -> ability is QuickInflateViewAbility }
            ?.forceTo<QuickInflateViewAbility>()
    if (inflateAbility != null) {
        InitViewHelper.init(
            this,
            inflateAbility.layoutId(),
            inflateAbility.layoutView(),
            inflateAbility.layoutViewCreate()
        )
    } else {
        //恢复时能力丢失返回空页面
        FrameLayout(this)
    }
}), BindViewModel<ViewModel> {
    val extraParameter: LinkedHashMap<String, Any> = linkedMapOf()

    companion object {
        internal var commonAbilityList: MutableList<QuickAbility> = mutableListOf()

        @Suppress("unused")
        fun registerCommonAbility(commonAbilityList: List<QuickAbility>) {
            QuickDialog.commonAbilityList.addAll(commonAbilityList)
        }
    }

    init {
        abilityList.addAll(0, commonAbilityList)
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
            it.onCreate(this)
            it.onResume(this)
        }
    }

    @CallSuper
    override fun onStart() {
        super.onStart()
        abilityList.filterIsInstance<QuickLifecycleAbility>().forEach {
            it.onStart(this)
        }
    }

    @CallSuper
    override fun onStop() {
        super.onStop()
        abilityList.filterIsInstance<QuickLifecycleAbility>().forEach {
            it.onPause(this)
            it.onStop(this)
        }
    }

    @CallSuper
    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        abilityList.filterIsInstance<QuickLifecycleAbility>().forEach {
            it.onDestroy(this)
        }
    }
}