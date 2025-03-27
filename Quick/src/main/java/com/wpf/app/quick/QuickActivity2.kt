package com.wpf.app.quick

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import androidx.annotation.CallSuper
import androidx.lifecycle.ViewModel
import com.wpf.app.quick.ability.QuickAbilityI
import com.wpf.app.quick.activity.QuickBaseActivity
import com.wpf.app.quickbind.interfaces.BindViewModel
import com.wpf.app.quickutil.ability.base.QuickAbility
import com.wpf.app.quickutil.ability.base.QuickGenerateViewAbility
import com.wpf.app.quickutil.ability.base.QuickInflateViewAbility
import com.wpf.app.quickutil.ability.base.QuickInitViewAbility
import com.wpf.app.quickutil.ability.base.QuickLifecycleAbility
import com.wpf.app.quickutil.helper.InitViewHelper
import com.wpf.app.quickutil.helper.generic.asTo
import com.wpf.app.quickutil.helper.generic.forceTo

abstract class QuickActivity2(
    private val abilityList1: MutableList<QuickAbility> = mutableListOf(),
) : QuickBaseActivity(
    layoutViewCreate = {
        val abilityList = (this as QuickAbilityI).abilityList
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
    }
), BindViewModel<ViewModel>, QuickAbilityI {
    override var abilityList: MutableList<QuickAbility> = mutableListOf()

    @Suppress("unused")
    val extraParameter: LinkedHashMap<String, Any> = linkedMapOf()

    companion object {
        internal var commonAbilityList: MutableList<QuickAbility> = mutableListOf()

        @Suppress("unused")
        fun registerCommonAbility(commonAbilityList: List<QuickAbility>) {
            QuickActivity2.commonAbilityList.addAll(commonAbilityList)
        }
    }

    init {
        abilityList.addAll(0, commonAbilityList)
        abilityList.addAll(initAbility())
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

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        abilityList.filterIsInstance<QuickLifecycleAbility>().forEach {
            it.onSaveInstanceState(this, outState)
        }
    }

    override fun onCreateWithSavedInstanceState(savedInstanceState: Bundle?) {
        super.onCreateWithSavedInstanceState(savedInstanceState)
        abilityList.filterIsInstance<QuickLifecycleAbility>().forEach {
            it.onRestoredInstanceState(this, savedInstanceState)
        }
    }
}

