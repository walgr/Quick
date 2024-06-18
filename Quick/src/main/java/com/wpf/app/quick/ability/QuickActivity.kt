package com.wpf.app.quick.ability

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.annotation.CallSuper
import androidx.lifecycle.ViewModel
import com.wpf.app.base.ability.base.QuickAbility
import com.wpf.app.base.ability.base.QuickGenerateViewAbility
import com.wpf.app.base.ability.base.QuickInflateViewAbility
import com.wpf.app.base.ability.base.QuickInitViewAbility
import com.wpf.app.base.ability.base.QuickLifecycleAbility
import com.wpf.app.quick.activity.QuickBaseActivity
import com.wpf.app.quickbind.interfaces.BindViewModel
import com.wpf.app.quickutil.helper.InitViewHelper
import com.wpf.app.quickutil.other.asTo
import com.wpf.app.quickutil.other.forceTo

open class QuickActivity(
    private val abilityList: MutableList<QuickAbility> = mutableListOf(),
) : QuickBaseActivity(
    layoutViewCreate = {
        val inflateAbility = abilityList.first { ability -> ability is QuickInflateViewAbility }
            .forceTo<QuickInflateViewAbility>()
        InitViewHelper.init(
            this,
            inflateAbility.layoutId(),
            inflateAbility.layoutView(),
            inflateAbility.layoutViewCreate()
        )
    }
), BindViewModel<ViewModel> {
    @Suppress("unused")
    val extraParameter: LinkedHashMap<String, Any> = linkedMapOf()

    companion object {
        internal var commonAbilityList: MutableList<QuickAbility> = mutableListOf()

        fun registerCommonAbility(vararg commonAbility: QuickAbility) {
            commonAbilityList.addAll(commonAbility)
        }
    }

    init {
        abilityList.addAll(0, commonAbilityList)
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
    override fun getViewModel(): ViewModel? {
        return abilityList.firstOrNull { it is BindViewModel<*> }?.asTo<BindViewModel<*>>()
            ?.getViewModel()
    }

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        abilityList.filterIsInstance<QuickLifecycleAbility>().forEach {
            it.onCreate(this)
        }
    }

    @CallSuper
    override fun onResume() {
        super.onResume()
        abilityList.filterIsInstance<QuickLifecycleAbility>().forEach {
            it.onResume(this)
        }
    }

    @CallSuper
    override fun onPause() {
        super.onPause()
        abilityList.filterIsInstance<QuickLifecycleAbility>().forEach {
            it.onPause(this)
        }
    }

    @CallSuper
    override fun onStop() {
        super.onStop()
        abilityList.filterIsInstance<QuickLifecycleAbility>().forEach {
            it.onStop(this)
        }
    }

    @CallSuper
    override fun onDestroy() {
        super.onDestroy()
        abilityList.filterIsInstance<QuickLifecycleAbility>().forEach {
            it.onDestroy(this)
        }
    }

    @CallSuper
    @Deprecated("Deprecated by Android")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        abilityList.filterIsInstance<QuickLifecycleAbility>().forEach {
            it.onActivityResult(this, requestCode, resultCode, data)
        }
    }

//    @CallSuper
//    override fun onSaveInstanceState(outState: Bundle) {
//        super.onSaveInstanceState(outState)
//        abilityList.filterIsInstance<QuickLifecycleAbility>().forEach {
//            it.onSaveInstanceState(outState)
//        }
//    }
}

