@file:Suppress("LeakingThis")

package com.wpf.app.quick.ability

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.wpf.app.base.Quick
import com.wpf.app.base.ability.base.QuickAbility
import com.wpf.app.base.ability.base.QuickGenerateViewAbility
import com.wpf.app.base.ability.base.QuickInflateViewAbility
import com.wpf.app.base.ability.base.QuickInitViewAbility
import com.wpf.app.quicknetwork.base.RequestCoroutineScope
import com.wpf.app.quickutil.helper.InitViewHelper
import com.wpf.app.quickutil.helper.match
import com.wpf.app.quickutil.helper.reset
import com.wpf.app.quickutil.other.forceTo
import com.wpf.app.quickutil.widget.wishLayoutParams
import kotlinx.coroutines.Job

@Suppress("CanBeParameter")
open class QuickView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    private val abilityList: MutableList<QuickAbility> = mutableListOf(),
) : FrameLayout(
    context, attrs, defStyleAttr
), RequestCoroutineScope, Quick {

    override var jobManager: MutableList<Job> = mutableListOf()

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

    private var childView: View? = null
    init {
        val inflateAbility = abilityList.first { ability -> ability is QuickInflateViewAbility }
            .forceTo<QuickInflateViewAbility>()
        childView = InitViewHelper.init(
            context,
            inflateAbility.layoutId(),
            inflateAbility.layoutView(),
            inflateAbility.layoutViewCreate()
        )
        childView!!.layoutParams = childView!!.wishLayoutParams<LayoutParams>().reset(match, match)
        abilityList.filterIsInstance<QuickGenerateViewAbility>().forEach {
            childView = it.generateContentView(this, childView!!)
        }
        addView(childView)
        abilityList.filterIsInstance<QuickGenerateViewAbility>().forEach {
            it.afterGenerateContentView(this, childView!!)
        }
        initView()
        abilityList.filterIsInstance<QuickInitViewAbility>().forEach {
            it.initView(this, childView!!)
        }
    }

    open fun initView() {

    }
}