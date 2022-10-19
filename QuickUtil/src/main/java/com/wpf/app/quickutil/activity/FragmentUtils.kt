package com.wpf.app.quickutil.activity

import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.Fragment
import java.io.Serializable
import java.util.ArrayList
import java.util.HashMap

/**
 * Created by 王朋飞 on 2022/6/21.
 */
object FragmentUtils {
    fun loadData(fragment: Fragment, data: HashMap<String, Any?>): Fragment {
        val bundle = Bundle()
        val keys: Set<String> = data.keys
        for (key in keys) {
            val value = data[key]
            if (value is ArrayList<*> && value.isNotEmpty() && value[0] is Parcelable) {
                bundle.putParcelableArrayList(key, value as ArrayList<out Parcelable>?)
            } else if (value is Parcelable) {
                bundle.putParcelable(key, value)
            } else if (value is Serializable) {
                bundle.putSerializable(key, value)
            }
        }
        fragment.setArguments(bundle)
        return fragment
    }
}