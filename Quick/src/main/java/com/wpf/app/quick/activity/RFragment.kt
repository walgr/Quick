package com.wpf.app.quick.activity

import android.view.View
import androidx.annotation.CallSuper
import com.wpf.app.quick.R
import com.wpf.app.quick.widgets.recyclerview.QuickRecyclerView

/**
 * Created by 王朋飞 on 2022/7/13.
 *
 */
open class RFragment(
    override val titleName: String = ""
) : QuickFragment(R.layout.fragment_recyclerview, titleName = titleName) {

    var mRecyclerView: QuickRecyclerView? = null

    @CallSuper
    override fun initView(view: View?) {
        mRecyclerView = view?.findViewById(R.id.recyclerView)
    }

    override fun getTitle(): String? {
        return titleName
    }
}