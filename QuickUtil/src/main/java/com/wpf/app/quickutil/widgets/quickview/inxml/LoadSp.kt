package com.wpf.app.quickutil.widgets.quickview.inxml

import android.content.Context
import android.text.TextUtils
import android.util.AttributeSet
import android.widget.TextView
import androidx.core.content.edit
import androidx.core.widget.doAfterTextChanged
import com.wpf.app.quickutil.widgets.quickview.ChildToParentGroup
import com.wpf.app.quickutil.base.asTo
import com.wpf.app.quickutil.bind.QuickBindWrap
import com.wpf.app.quickutil.helper.attribute.SpViewAttributeHelper

/**
 * 给子View注入Sp内容
 */
class LoadSp2Text @JvmOverloads constructor(
    mContext: Context,
    val attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : ChildToParentGroup(
    mContext, attributeSet, defStyleAttr
) {

    private var attributeHelper: SpViewAttributeHelper =
        SpViewAttributeHelper(context, attributeSet!!)

    private val sharedPreference = context.getSharedPreferences(
        if (TextUtils.isEmpty(attributeHelper.fileName)) QuickBindWrap.getBindSpFileName() else attributeHelper.fileName,
        Context.MODE_PRIVATE
    )
    private val spData: String? =
        sharedPreference.getString(attributeHelper.bindKey, attributeHelper.defaultString)

    override fun onFinishInflate() {
        super.onFinishInflate()
        val firstChild = getChildAt(0)
        if (firstChild is TextView) {
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
}