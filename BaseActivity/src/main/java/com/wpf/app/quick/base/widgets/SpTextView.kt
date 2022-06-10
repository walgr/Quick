package com.wpf.app.quick.base.widgets

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import com.wpf.app.quick.base.helper.SpViewAttributeHelper
import com.wpf.app.quick.base.helper.ViewSpHelper

/**
 * Created by 王朋飞 on 2022/6/10.
 *
 */
class SpTextView @JvmOverloads constructor(
    context: Context,
    attributes: AttributeSet? = null,
    defStyleAttr: Int = 0,
    override var attributeHelper: SpViewAttributeHelper? = null
) : AppCompatTextView(context, attributes, defStyleAttr), ViewSpHelper {

    init {
        attributes?.let {
            initAttributeHelper(context, attributes)
        }
        attributeHelper?.bindKey?.let {
            val value = getValue(context)
            if (value.isNotEmpty()) text = value
        }
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                println("onTextChanged"+s)
                s?.let {
                    updateValue(context, s.toString())
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })
    }
}