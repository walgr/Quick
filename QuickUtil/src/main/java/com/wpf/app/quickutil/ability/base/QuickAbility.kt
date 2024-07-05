package com.wpf.app.quickutil.ability.base

import java.io.Serializable

fun <T : QuickAbility> MutableList<T>.with(others: MutableList<T>): MutableList<T> {
    others.filter { it is Unique }.map { it.getPrimeKey() }.forEach { otherPrimeKey ->
        this.remove(this.find { it.getPrimeKey() == otherPrimeKey })
    }
    this.addAll(others)
    return this
}

fun <T : QuickAbility> MutableList<T>.with(other: T): MutableList<T> {
    if (other is Unique) {
        this.remove(this.find { it.getPrimeKey() == other.getPrimeKey() })
    }
    this.add(other)
    return this
}

fun <T : QuickAbility> T.with(others: MutableList<T>): MutableList<T> {
    if (this is Unique) {
        others.remove(others.find { it.getPrimeKey() == this.getPrimeKey() })
    }
    others.add(this)
    return others
}

fun <T : QuickAbility> T.with(other: T): MutableList<T> {
    val abilityList = mutableListOf<T>()
    abilityList.add(this)
    if (other !is Unique || other.getPrimeKey() != this.getPrimeKey()) {
        abilityList.add(other)
    }
    return abilityList
}

interface QuickAbility: Serializable {
    fun getPrimeKey(): String
}