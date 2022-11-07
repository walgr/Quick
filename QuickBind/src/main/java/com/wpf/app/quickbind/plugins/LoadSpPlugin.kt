package com.wpf.app.quickbind.plugins

import android.content.Context
import android.text.TextUtils
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.wpf.app.quickbind.QuickBind
import com.wpf.app.quickbind.annotations.LoadSp
import java.lang.reflect.Field

/**
 * Created by 王朋飞 on 2022/7/13.
 *
 */
class LoadSpPlugin : BasePlugin {

    override fun dealField(
        obj: Any,
        viewModel: ViewModel?,
        field: Field
    ) {
        try {
            val loadSpAnn: LoadSp = field.getAnnotation(LoadSp::class.java) ?: return
            val context = getContext(obj) ?: return
            var fileName = QuickBind.getBindSpFileName()
            if (!TextUtils.isEmpty(loadSpAnn.fileName)) {
                fileName = loadSpAnn.fileName
            }
            val value = context.getSharedPreferences(fileName, Context.MODE_PRIVATE)
                .getString(loadSpAnn.key, loadSpAnn.defaultValue)
            field.isAccessible = true
            if (field.type == String::class.java) {
                field[getRealObj(obj, viewModel)] = value
            } else {
                //不是要String要数据类
                val valueObj: Any = Gson().fromJson(value, field.genericType)
                field[getRealObj(obj, viewModel)] = valueObj
            }
            return
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return
    }
}