package com.wpf.app.quickwork.widget.theme

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import com.wpf.app.quickwork.widget.theme.QuickTextThemeBase
import com.wpf.app.quickwork.widget.theme.QuickTextThemeI

open class QuickThemeTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    theme: QuickTextThemeI? = null
) : AppCompatTextView(context, attrs, defStyleAttr), QuickTextThemeBase {

    override var curTheme: QuickTextThemeI? = null
    override var textView: TextView = this

    init {
        super.initTextTheme(context, attrs, theme)
    }
}