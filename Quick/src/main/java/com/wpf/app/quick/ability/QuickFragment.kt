package com.wpf.app.quick.ability

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.annotation.CallSuper
import androidx.lifecycle.ViewModel
import com.wpf.app.quick.ability.ex.QuickActivityAbility
import com.wpf.app.quick.ability.ex.QuickFragmentAbility
import com.wpf.app.quick.ability.ex.QuickInflateViewAbility
import com.wpf.app.quick.activity.QuickBaseFragment
import com.wpf.app.quickbind.interfaces.BindViewModel
import com.wpf.app.quickutil.other.asTo
import com.wpf.app.quickutil.other.forceTo

open class QuickFragment(
    private val abilityList: List<QuickActivityAbility> = mutableListOf(), titleName: String = ""
) : QuickBaseFragment(
    layoutId = abilityList.find { it is QuickInflateViewAbility }!!
        .forceTo<QuickInflateViewAbility>().layoutId(),
    layoutView = abilityList.find { it is QuickInflateViewAbility }!!
        .forceTo<QuickInflateViewAbility>().layoutView(),
    layoutViewInContext = abilityList.find { it is QuickInflateViewAbility }!!
        .forceTo<QuickInflateViewAbility>().layoutViewInContext(),
    titleName = titleName
), BindViewModel<ViewModel> {
    internal val extraParameter: LinkedHashMap<String, Any> = linkedMapOf()

    override fun getViewModel(): ViewModel? {
        return abilityList.find { it is BindViewModel<*> }?.asTo<BindViewModel<*>>()?.getViewModel()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        abilityList.forEach {
            it.onSaveInstanceState(outState)
        }
    }

    override fun onResume() {
        super.onResume()
        abilityList.forEach {
            it.onResume()
        }
    }

    override fun onPause() {
        super.onPause()
        abilityList.forEach {
            it.onPause()
        }
    }

    override fun onStop() {
        super.onStop()
        abilityList.forEach {
            it.onStop()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        abilityList.forEach {
            it.onDestroy()
        }
    }

    @Deprecated("Deprecated by Android")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        abilityList.forEach {
            it.onActivityResult(requestCode, resultCode, data)
        }
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

    @Deprecated("Deprecated in Java")
    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        abilityList.filterIsInstance<QuickFragmentAbility>().forEach {
            it.setUserVisibleHint(isVisibleToUser)
        }
    }
}

