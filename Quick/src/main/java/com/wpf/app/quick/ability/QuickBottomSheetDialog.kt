package com.wpf.app.quick.ability

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.annotation.CallSuper
import androidx.annotation.StyleRes
import com.wpf.app.base.ability.base.QuickAbility
import com.wpf.app.base.ability.base.QuickInflateViewAbility
import com.wpf.app.base.ability.base.QuickLifecycleAbility
import com.wpf.app.quickdialog.QuickBaseBottomSheetDialog
import com.wpf.app.quickutil.helper.InitViewHelper
import com.wpf.app.quickutil.other.forceTo

open class QuickBottomSheetDialog(
    context: Context,
    @StyleRes themeId: Int = 0,
    private val abilityList: List<QuickAbility> = mutableListOf(),
) : QuickBaseBottomSheetDialog(context = context, themeId = themeId, layoutViewCreate = {
    val inflateAbility = abilityList.first { ability -> ability is QuickInflateViewAbility }
        .forceTo<QuickInflateViewAbility>()
    InitViewHelper.init(
        this,
        inflateAbility.layoutId(),
        inflateAbility.layoutView(),
        inflateAbility.layoutViewCreate()
    )
}) {
    internal val extraParameter: LinkedHashMap<String, Any> = linkedMapOf()

    @CallSuper
    override fun generateContentView(view: View): View {
        val inflateViewAbility =
            abilityList.first { it is QuickInflateViewAbility }.forceTo<QuickInflateViewAbility>()
        return inflateViewAbility.generateContentView(this, view)
    }

    @CallSuper
    override fun initView(view: View) {
        super.initView(view)
        abilityList.filterIsInstance<QuickLifecycleAbility>().forEach {
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