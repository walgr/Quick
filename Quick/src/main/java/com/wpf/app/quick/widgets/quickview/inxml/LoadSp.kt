package com.wpf.app.quick.widgets.quickview.inxml

import android.content.Context
import android.text.TextUtils
import android.util.AttributeSet
import android.widget.TextView
import com.wpf.app.quick.helper.attribute.SpViewAttributeHelper
import com.wpf.app.quick.widgets.quickview.AddToParentGroup
import com.wpf.app.quickbind.QuickBind
import com.wpf.app.quickutil.base.asTo

/**
 * 给子View注入Sp内容
 */
class LoadSp2Text @JvmOverloads constructor(
    mContext: Context,
    val attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : AddToParentGroup(
    mContext, attributeSet, defStyleAttr
) {

    private var attributeHelper: SpViewAttributeHelper =
        SpViewAttributeHelper(context, attributeSet!!)

    private val spData: String? = context.getSharedPreferences(
        if (TextUtils.isEmpty(attributeHelper.fileName)) QuickBind.getBindSpFileName() else attributeHelper.fileName,
        Context.MODE_PRIVATE
    ).getString(attributeHelper.bindKey, attributeHelper.defaultString)

    override fun onFinishInflate() {
        super.onFinishInflate()
        if (getChildAt(0) is TextView) {
            //只能套在基于TextView的View上
            getChildAt(0).asTo<TextView>()?.text = spData
        }
    }
}