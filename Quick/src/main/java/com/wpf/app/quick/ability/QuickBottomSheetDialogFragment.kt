package com.wpf.app.quick.ability

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.annotation.CallSuper
import androidx.lifecycle.ViewModel
import com.wpf.app.base.ability.base.QuickAbility
import com.wpf.app.base.ability.base.QuickInflateViewAbility
import com.wpf.app.base.ability.base.QuickLifecycleAbility
import com.wpf.app.base.ability.base.QuickViewAbility
import com.wpf.app.quick.ability.ex.base.QuickFragmentAbility
import com.wpf.app.quickbind.interfaces.BindViewModel
import com.wpf.app.quickdialog.QuickBaseBottomSheetDialogFragment
import com.wpf.app.quickutil.helper.InitViewHelper
import com.wpf.app.quickutil.other.asTo
import com.wpf.app.quickutil.other.forceTo

open class QuickBottomSheetDialogFragment(
    private val abilityList: MutableList<QuickAbility> = mutableListOf()
) : QuickBaseBottomSheetDialogFragment(
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
        val inflateViewAbility =
            abilityList.first { it is QuickInflateViewAbility }.forceTo<QuickInflateViewAbility>()
        return inflateViewAbility.generateContentView(this, view.forceTo<View>())
    }

    @CallSuper
    override fun initView(view: View) {
        abilityList.filterIsInstance<QuickViewAbility>().forEach {
            it.afterGenerateContentView(this, view)
        }
        abilityList.filterIsInstance<QuickViewAbility>().forEach {
            it.initView(this, view)
        }
    }

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        abilityList.filterIsInstance<QuickLifecycleAbility>().forEach {
            it.onResume()
        }
    }

    @CallSuper
    override fun onResume() {
        super.onResume()
        abilityList.filterIsInstance<QuickLifecycleAbility>().forEach {
            it.onResume()
        }
    }

    @CallSuper
    override fun onPause() {
        super.onPause()
        abilityList.filterIsInstance<QuickLifecycleAbility>().forEach {
            it.onPause()
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
    override fun onDestroy() {
        super.onDestroy()
        abilityList.filterIsInstance<QuickLifecycleAbility>().forEach {
            it.onDestroy()
        }
    }

//    @CallSuper
//    override fun onSaveInstanceState(outState: Bundle) {
//        super.onSaveInstanceState(outState)
//        abilityList.filterIsInstance<QuickLifecycleAbility>().forEach {
//            it.onSaveInstanceState(outState)
//        }
//    }

    @CallSuper
    @Deprecated("Deprecated by Android")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        abilityList.filterIsInstance<QuickLifecycleAbility>().forEach {
            it.onActivityResult(requestCode, resultCode, data)
        }
    }

    @CallSuper
    @Deprecated("Deprecated in Android")
    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        abilityList.filterIsInstance<QuickFragmentAbility>().forEach {
            it.setUserVisibleHint(isVisibleToUser)
        }
    }
}