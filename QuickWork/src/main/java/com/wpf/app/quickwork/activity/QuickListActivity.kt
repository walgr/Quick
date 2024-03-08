package com.wpf.app.quickwork.activity

import android.widget.RelativeLayout
import com.wpf.app.quickrecyclerview.QuickRecyclerView
import com.wpf.app.quickrecyclerview.data.QuickItemData
import com.wpf.app.quickutil.helper.layoutParams
import com.wpf.app.quickutil.helper.matchLayoutParams
import com.wpf.app.quickwork.widget.QuickTitleView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

open class QuickListActivity(
    listBuilder: suspend QuickRecyclerView.() -> List<QuickItemData>,
    titleName: String = "",
    titleBuilder: (QuickTitleView.() -> Unit)? = null,
    showTitle: Boolean = true,
) : QuickTitleActivity(
    titleName = titleName, titleBuilder = titleBuilder, contentBuilder = {
        addView(QuickRecyclerView(context).apply {
            layoutParams = layoutParams<RelativeLayout.LayoutParams>(matchLayoutParams)
            CoroutineScope(Dispatchers.IO).launch {
                val listData = listBuilder.invoke(this@apply).toMutableList()
                withContext(Dispatchers.Main) {
                    setData(listData)
                }
            }
        })
    }, showTitle = showTitle
) {

    open fun initList(listView: QuickRecyclerView) {

    }
}