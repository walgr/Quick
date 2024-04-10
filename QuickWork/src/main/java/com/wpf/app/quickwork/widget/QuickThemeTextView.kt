package com.wpf.app.quickwork.widget

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import com.wpf.app.quickwork.widget.theme.QuickTextThemeBase

open class QuickThemeTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    theme: QuickTextThemeBase.QuickTextTheme? = null
) : AppCompatTextView(context, attrs, defStyleAttr), QuickTextThemeBase {

    override var curTheme: QuickTextThemeBase.QuickTextTheme? = null
    override var textView: TextView = this

    init {
        initTheme(context, attrs, theme)
    }
}