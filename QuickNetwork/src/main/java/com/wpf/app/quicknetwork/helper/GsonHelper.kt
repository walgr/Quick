package com.wpf.app.quicknetwork.helper

import com.google.gson.Gson
import com.google.gson.GsonBuilder

object GsonHelper {

    fun getDisableHtmlEscaping(): Gson {
        return GsonBuilder().disableHtmlEscaping().create()
    }

    val htmlCharacters = mapOf(
        "&middot;" to "·",
        "&quot;" to "\"",
        "&amp;" to "&",
    )

    fun decodeHTMLCharacter(json: String): String {
        var result = json
        htmlCharacters.forEach {
            result = result.replace(it.key, it.value, false)
        }
        return result
    }
}