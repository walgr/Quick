package com.wpf.app.quick.ability

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.annotation.CallSuper
import androidx.lifecycle.ViewModel
import com.wpf.app.quick.activity.QuickFragment
import com.wpf.app.quick.activity.viewmodel.ViewLifecycle
import com.wpf.app.quickbind.interfaces.BindViewModel
import com.wpf.app.quickutil.other.asTo
import com.wpf.app.quickutil.other.forceTo

open class QuickAbilityFragment(
    private val abilityList: List<QuickActivityAbility> = mutableListOf(), titleName: String = ""
) : QuickFragment(
    layoutId = abilityList.find { it is QuickInflateViewAbility }!!
        .forceTo<QuickInflateViewAbility>().layoutId(),
    layoutView = abilityList.find { it is QuickInflateViewAbility }!!
        .forceTo<QuickInflateViewAbility>().layoutView(),
    layoutViewInContext = abilityList.find { it is QuickInflateViewAbility }!!
        .forceTo<QuickInflateViewAbility>().layoutViewInContext(),
    titleName = titleName
), BindViewModel<ViewModel>, BindingAbility {
    internal val extraParameter: LinkedHashMap<String, Any> = linkedMapOf()

    override fun getViewModel(): ViewModel? {
        return abilityList.find { it is BindViewModel<*> }?.asTo<BindViewModel<*>>()?.getViewModel()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        abilityList.find { it is ViewLifecycle }?.forceTo<ViewLifecycle>()
            ?.onSaveInstanceState(outState)
    }

    override fun onResume() {
        super.onResume()
        abilityList.find { it is ViewLifecycle }?.forceTo<ViewLifecycle>()?.onResume()
    }

    override fun onPause() {
        super.onPause()
        abilityList.find { it is ViewLifecycle }?.forceTo<ViewLifecycle>()?.onPause()
    }

    override fun onStop() {
        super.onStop()
        abilityList.find { it is ViewLifecycle }?.forceTo<ViewLifecycle>()?.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        abilityList.find { it is ViewLifecycle }?.forceTo<ViewLifecycle>()?.onDestroy()
    }

    @Deprecated("Deprecated by Android")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        abilityList.find { it is ViewLifecycle }?.forceTo<ViewLifecycle>()
            ?.onActivityResult(requestCode, resultCode, data)
    }

    @CallSuper
    override fun dealContentView(view: View) {
        val newView: View =
            abilityList.find { it is QuickInflateViewAbility }!!.forceTo<QuickInflateViewAbility>()
                .beforeDealContentView(this, view.forceTo())
        super.dealContentView(newView)
        abilityList.forEach {
            it.dealContentView(this, newView)
        }
    }

    @CallSuper
    override fun initView(view: View) {
        abilityList.forEach {
            it.initView(view)
        }
    }

    override fun dealBind(): Boolean {
        return abilityList.find { it is QuickDealBindAbility }?.forceTo<QuickDealBindAbility>()
            ?.dealBind() ?: true
    }
}

