package com.wpf.app.quickrecyclerview.ability

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.wpf.app.base.ability.base.QuickAbility
import com.wpf.app.base.ability.base.Unique
import com.wpf.app.quickrecyclerview.QuickAdapter
import com.wpf.app.quickrecyclerview.ability.base.QuickItemAbility
import com.wpf.app.quickrecyclerview.data.QuickAbilityData
import com.wpf.app.quickrecyclerview.data.QuickItemData
import com.wpf.app.quickrecyclerview.holder.QuickViewHolder
import com.wpf.app.quickutil.other.asTo

fun <T : QuickAbilityData> swap(
    canSwap: T.() -> Boolean = { true },
): MutableList<QuickAbility> {
    return mutableListOf(
        object : QuickItemAbility<QuickAbilityData>, Unique {
            override fun getPrimeKey() = "swap"

            override fun beforeHolderOnCreateHolder(
                holder: QuickViewHolder<QuickItemData>,
                selfOnlyBase: QuickAbilityData
            ) {
                super.beforeHolderOnCreateHolder(holder, selfOnlyBase)
                val isAddSwapKey = "isAddSwap"
                val isAddSwap: Boolean = selfOnlyBase.getData(isAddSwapKey, false)
                if (isAddSwap) return
                selfOnlyBase.putData(isAddSwapKey, true)
                ItemTouchHelper(object : ItemTouchHelper.Callback() {
                    override fun getMovementFlags(
                        recyclerView: RecyclerView,
                        viewHolder: RecyclerView.ViewHolder
                    ): Int {
                        val adapter =
                            recyclerView.adapter.asTo<QuickAdapter>() ?: return makeMovementFlags(0, 0)
                        val firstData = adapter.getData()?.first { it is QuickAbilityData }
                            ?.asTo<QuickAbilityData>() ?: return makeMovementFlags(0, 0)
                        var dragFlags = 0
                        firstData.getViewRealData(viewHolder)?.let {
                            dragFlags = if (canSwap.invoke(it as T)) (ItemTouchHelper.UP or ItemTouchHelper.DOWN) else 0
                        }
                        return makeMovementFlags(dragFlags, 0)
                    }

                    override fun onMove(
                        recyclerView: RecyclerView,
                        viewHolder: RecyclerView.ViewHolder,
                        target: RecyclerView.ViewHolder
                    ): Boolean {
                        recyclerView.adapter.asTo<QuickAdapter>()?.onMove(
                            viewHolder.bindingAdapterPosition,
                            target.bindingAdapterPosition
                        )
                        return true
                    }

                    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                    }

                }).apply {
                    val oldLayoutManager = selfOnlyBase.getRecyclerView()?.layoutManager
                    selfOnlyBase.getRecyclerView()?.layoutManager = null
                    attachToRecyclerView(selfOnlyBase.getRecyclerView())
                    selfOnlyBase.getRecyclerView()?.layoutManager = oldLayoutManager
                }
            }
        }
    )
}