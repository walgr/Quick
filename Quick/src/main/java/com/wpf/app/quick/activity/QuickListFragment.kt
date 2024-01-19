package com.wpf.app.quick.activity

import android.view.View
import androidx.annotation.CallSuper
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import com.wpf.app.quickrecyclerview.QuickRecyclerView
import com.wpf.app.quickrecyclerview.QuickRefreshRecyclerView
import com.wpf.app.quickrecyclerview.data.QuickItemData
import com.wpf.app.quickrecyclerview.data.RequestData
import com.wpf.app.quickutil.view.matchLayoutParams
import com.wpf.app.quickutil.bind.runOnContext
import com.wpf.app.quickutil.view.allChild

/**
 * Created by 王朋飞 on 2022/7/13.
 *
 */
open class QuickListFragment @JvmOverloads constructor(
    private val dataList: List<QuickItemData>? = null,
    private val dataLoader: (RequestData.() -> List<QuickItemData>?)? = null,
    @LayoutRes layoutId: Int = 0,
    layoutView: View? = null,
    @IdRes val listId: Int = 0,
    titleName: String = ""
) : QuickFragment(layoutId, layoutView, layoutViewInContext = runOnContext {
    QuickRefreshRecyclerView(it).apply {
        layoutParams = matchLayoutParams
    }
}, titleName = titleName) {

    private var mRecyclerView: QuickRecyclerView? = null
    private val requestData = RequestData()

    @CallSuper
    override fun initView(view: View?) {
        mRecyclerView = if (layoutId == 0) {
            view as? QuickRefreshRecyclerView
        } else {
            if (listId == 0) {
                view?.allChild()?.find { it is QuickRecyclerView } as? QuickRecyclerView
            } else {
                view?.findViewById(listId)
            }
        }
        if (dataLoader == null) {
            mRecyclerView?.setNewData(dataList)
        } else {
            mRecyclerView?.setNewData(dataLoader.invoke(requestData))
        }
    }

    override fun getTitle(): String? {
        return titleName
    }
}