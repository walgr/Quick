package com.wpf.app.quick.ability

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import com.wpf.app.base.Quick
import com.wpf.app.base.ability.base.QuickAbility
import com.wpf.app.base.ability.base.QuickGenerateViewAbility
import com.wpf.app.base.ability.base.QuickInflateViewAbility
import com.wpf.app.base.ability.base.QuickInitViewAbility
import com.wpf.app.quickutil.helper.InitViewHelper
import com.wpf.app.quickutil.helper.match
import com.wpf.app.quickutil.helper.reset
import com.wpf.app.quickutil.other.forceTo
import com.wpf.app.quickutil.widget.wishLayoutParams

@Suppress("LeakingThis")
open class QuickView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    abilityList: MutableList<QuickAbility> = mutableListOf(),
) : FrameLayout(
    context, attrs, defStyleAttr
), LifecycleOwner, Quick {
    @Suppress("unused")
    val extraParameter: LinkedHashMap<String, Any> = linkedMapOf()

    companion object {
        internal var commonAbilityList: MutableList<QuickAbility> = mutableListOf()

        @Suppress("unused")
        fun registerCommonAbility(commonAbilityList: List<QuickAbility>) {
            QuickView.commonAbilityList.addAll(commonAbilityList)
        }
    }

    init {
        abilityList.addAll(0, commonAbilityList)
    }

    init {
        val inflateAbility = abilityList.first { ability -> ability is QuickInflateViewAbility }
            .forceTo<QuickInflateViewAbility>()
        var view = InitViewHelper.init(
            context,
            inflateAbility.layoutId(),
            inflateAbility.layoutView(),
            inflateAbility.layoutViewCreate()
        )
        view.layoutParams = view.wishLayoutParams<LayoutParams>().reset(match, match)
        abilityList.filterIsInstance<QuickGenerateViewAbility>().forEach {
            view = it.generateContentView(this, view)
        }
        addView(view)
        abilityList.filterIsInstance<QuickGenerateViewAbility>().forEach {
            it.afterGenerateContentView(this, view)
        }
        initView()
        abilityList.filterIsInstance<QuickInitViewAbility>().forEach {
            it.initView(this, view)
        }
    }

    open fun initView() {

    }

    override val lifecycle: Lifecycle
        get() = LifecycleRegistry(this)
}