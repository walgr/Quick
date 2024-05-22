package com.wpf.app.quickwork.widget.theme

import android.content.Context
import android.util.AttributeSet
import android.widget.EditText
import androidx.appcompat.R
import androidx.appcompat.widget.AppCompatEditText

open class QuickThemeEditView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.editTextStyle,
    theme: QuickEditThemeI? = null
) : AppCompatEditText(context, attrs, defStyleAttr), QuickEditThemeBase {

    override var curTheme: QuickTextThemeI? = null
    override var editText: EditText = this

    init {
        super.initEditTheme(context, attrs, theme)
    }
}