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
import com.wpf.app.quick.activity.QuickBaseActivity
import com.wpf.app.quickbind.interfaces.BindViewModel
import com.wpf.app.quickutil.helper.InitViewHelper
import com.wpf.app.quickutil.other.asTo
import com.wpf.app.quickutil.other.forceTo
import com.wpf.app.quickutil.run.runOnContext

open class QuickActivity(
    private val abilityList: List<QuickAbility> = mutableListOf(), titleName: String = "",
) : QuickBaseActivity(
        layoutViewInContext = runOnContext {
        val inflateAbility = abilityList.first { ability -> ability is QuickInflateViewAbility }
            .forceTo<QuickInflateViewAbility>()
        InitViewHelper.init(
            this,
            inflateAbility.layoutId(),
            inflateAbility.layoutView(),
            inflateAbility.layoutViewInContext()
        )
    }, titleName = titleName
), BindViewModel<ViewModel> {
    internal val extraParameter: LinkedHashMap<String, Any> = linkedMapOf()

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

    override fun getViewModel(): ViewModel? {
        return abilityList.firstOrNull { it is BindViewModel<*> }?.asTo<BindViewModel<*>>()?.getViewModel()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        abilityList.filterIsInstance<QuickLifecycleAbility>().forEach {
            it.onCreate()
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

    @Deprecated("Deprecated by Android")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        abilityList.filterIsInstance<QuickLifecycleAbility>().forEach {
            it.onActivityResult(requestCode, resultCode, data)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        abilityList.filterIsInstance<QuickLifecycleAbility>().forEach {
            it.onSaveInstanceState(outState)
        }
    }
}

