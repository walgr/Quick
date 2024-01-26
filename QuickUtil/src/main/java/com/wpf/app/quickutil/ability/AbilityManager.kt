package com.wpf.app.quickutil.ability

import android.content.Context

object AbilityManager {

    fun <T> dealByThisAndContext(
        ability: List<QuickContextAbility<T, *>>,
        context: Context,
        cur: T
    ) {
        ability.forEach {
            it.run(context, cur)
        }
    }
}