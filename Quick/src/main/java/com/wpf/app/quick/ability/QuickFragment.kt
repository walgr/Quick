package com.wpf.app.quick.ability

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.annotation.CallSuper
import androidx.lifecycle.ViewModel
import com.wpf.app.base.ability.base.QuickAbility
import com.wpf.app.base.ability.base.QuickGenerateViewAbility
import com.wpf.app.base.ability.base.QuickInflateViewAbility
import com.wpf.app.base.ability.base.QuickLifecycleAbility
import com.wpf.app.base.ability.base.QuickInitViewAbility
import com.wpf.app.quick.ability.ex.base.QuickFragmentAbility
import com.wpf.app.quick.activity.QuickBaseFragment
import com.wpf.app.quickbind.interfaces.BindViewModel
import com.wpf.app.quickutil.helper.InitViewHelper
import com.wpf.app.quickutil.other.asTo
import com.wpf.app.quickutil.other.forceTo

open class QuickFragment(
    private val abilityList: MutableList<QuickAbility> = mutableListOf()
) : QuickBaseFragment(
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        abilityList.filterIsInstance<QuickLifecycleAbility>().forEach {
            it.onResume()
        }
    }

    override fun onResume() {
        super.onResume()
        abilityList.filterIsInstance<QuickLifecycleAbility>().forEach {
            it.onResume()
        }
    }

    override fun onPause() {
        super.onPause()
        abilityList.filterIsInstance<QuickLifecycleAbility>().forEach {
            it.onPause()
        }
    }

    override fun onStop() {
        super.onStop()
        abilityList.filterIsInstance<QuickLifecycleAbility>().forEach {
            it.onStop()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        abilityList.filterIsInstance<QuickLifecycleAbility>().forEach {
            it.onDestroy()
        }
    }

//    override fun onSaveInstanceState(outState: Bundle) {
//        super.onSaveInstanceState(outState)
//        abilityList.filterIsInstance<QuickLifecycleAbility>().forEach {
//            it.onSaveInstanceState(outState)
//        }
//    }

    @Deprecated("Deprecated by Android")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        abilityList.filterIsInstance<QuickLifecycleAbility>().forEach {
            it.onActivityResult(requestCode, resultCode, data)
        }
    }

    @Deprecated("Deprecated in Android")
    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        abilityList.filterIsInstance<QuickFragmentAbility>().forEach {
            it.setUserVisibleHint(isVisibleToUser)
        }
    }
}

