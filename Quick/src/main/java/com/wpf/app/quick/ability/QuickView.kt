package com.wpf.app.quick.ability

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import com.wpf.app.base.QuickView
import com.wpf.app.base.ability.base.QuickAbility
import com.wpf.app.base.ability.base.QuickInflateViewAbility
import com.wpf.app.base.ability.base.QuickViewAbility
import com.wpf.app.quickutil.helper.InitViewHelper
import com.wpf.app.quickutil.helper.match
import com.wpf.app.quickutil.helper.reset
import com.wpf.app.quickutil.other.forceTo
import com.wpf.app.quickutil.widget.wishLayoutParams

open class QuickView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    abilityList: List<QuickAbility> = mutableListOf(),
) : FrameLayout(
    context, attrs, defStyleAttr
), LifecycleOwner, QuickView {
    val extraParameter: LinkedHashMap<String, Any> = linkedMapOf()

    init {
        val inflateAbility = abilityList.first { ability -> ability is QuickInflateViewAbility }
            .forceTo<QuickInflateViewAbility>()
        val view = InitViewHelper.init(
            context,
            inflateAbility.layoutId(),
            inflateAbility.layoutView(),
            inflateAbility.layoutViewCreate()
        )
        view.layoutParams = view.wishLayoutParams<LayoutParams>().reset(match, match)
        addView(view)
        inflateAbility.generateContentView(this, view)
        initView()
        abilityList.filterIsInstance<QuickViewAbility>().forEach {
            it.initView(this, view)
        }
    }

    open fun initView() {

    }

    override val lifecycle: Lifecycle
        get() = LifecycleRegistry(this)
}