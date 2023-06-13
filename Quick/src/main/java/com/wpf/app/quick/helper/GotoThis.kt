package com.wpf.app.quick.helper

interface GotoThis {

    fun gotoActivity(): Class<*> {
        return this.javaClass
    }
}