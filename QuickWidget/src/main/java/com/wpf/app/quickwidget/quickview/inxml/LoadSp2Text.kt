package com.wpf.app.quickwidget.quickview.inxml

import android.content.Context
import android.graphics.Canvas
import android.text.TextUtils
import android.util.AttributeSet
import android.widget.TextView
import androidx.core.content.edit
import androidx.core.widget.doAfterTextChanged
import com.wpf.app.quickwidget.quickview.ChildToParentGroup
import com.wpf.app.quickutil.other.asTo
import com.wpf.app.quickutil.bind.QuickBindWrap
import com.wpf.app.quickutil.helper.attribute.AutoGetAttribute
import com.wpf.app.quickwidget.R
import com.wpf.app.quickwidget.quickview.Only1Child

/**
 * 给子View注入Sp内容
 */
class LoadSp2Text @JvmOverloads constructor(
    mContext: Context,
    val attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : Only1Child<TextView>(
    mContext, attributeSet, defStyleAttr
) {

    private var attributeHelper: SpViewAttribute =
        SpViewAttribute(context, attributeSet!!)

    private val sharedPreference = context.getSharedPreferences(
        if (TextUtils.isEmpty(attributeHelper.fileName)) QuickBindWrap.getBindSpFileName() else attributeHelper.fileName,
        Context.MODE_PRIVATE
    )
    private val spData: String? =
        sharedPreference.getString(attributeHelper.bindKey, attributeHelper.defaultString)

    override fun onFinishInflate() {
        super.onFinishInflate()
        val firstChild = getChildAtInShadow(0)
        //只能套在基于TextView的View上
        firstChild.asTo<TextView>()?.text = spData
        if (attributeHelper.bindData2Sp == true) {
            firstChild.asTo<TextView>()?.doAfterTextChanged {
                sharedPreference.edit {
                    putString(attributeHelper.bindKey, it?.toString() ?: "")
                }
            }
        }
    }
}

class SpViewAttribute(
    context: Context,
    attributeSet: AttributeSet
): AutoGetAttribute(context, attributeSet, R.styleable.LoadSp2Text) {

    val fileName: String? = null
    val bindKey: String? = null
    val defaultString: String? = null
    val bindData2Sp: Boolean? = null
}