package com.wpf.app.quickwidget.selectview.helper

import com.wpf.app.quickwidget.selectview.data.QuickParentSelectData

/**
 * Created by 王朋飞 on 2022/9/21.
 *
 */
object ParentChildDataHelper {

    /**
     * @param childInOne 是否展示所有子项 只支持父子2级数据
     */
    fun initData(
        parentList: List<QuickParentSelectData>?,
        childInOne: Boolean = false,
        addParentTitle: Boolean = childInOne
    ) {
        parentList?.forEach { parent ->
            parent.isInOne = childInOne
            parent.childList?.forEach { child ->
                child.parent = parent
                child.isInOne = childInOne
            }
        }
        if (childInOne) {
            parentList?.let {
                if (parentList.isNotEmpty()) {
                    if (addParentTitle) {
                        parentList.forEach { parent ->
                            if (parent.childList != null && parent.asTitleViewInChild() != null) {
                                parent.childList?.add(
                                    0,
                                    parent.asTitleViewInChild()!!.also {
                                        it.parent = parent
                                        it.isInOne = true
                                    })
                            }
                        }
                    }
                }
            }
        }
    }
}