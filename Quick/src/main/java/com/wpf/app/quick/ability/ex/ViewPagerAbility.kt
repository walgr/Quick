package com.wpf.app.quick.ability.ex

import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.wpf.app.quick.R
import com.wpf.app.quick.activity.QuickView
import com.wpf.app.quick.helper.getFragmentManager
import com.wpf.app.quick.helper.getLifecycle
import com.wpf.app.quickbind.utils.getFragment
import com.wpf.app.quickbind.viewpager.QuickViewPager
import com.wpf.app.quickbind.viewpager.ViewPagerSize
import com.wpf.app.quickbind.viewpager.adapter.FragmentsAdapter
import com.wpf.app.quickbind.viewpager.adapter.FragmentsStateAdapter
import com.wpf.app.quickbind.viewpager2.ViewPagerSize2
import com.wpf.app.quickutil.helper.matchWrapLayoutParams
import com.wpf.app.quickutil.helper.wrapContentHeightParams
import com.wpf.app.quickutil.other.forceTo

inline fun <reified T : Fragment> LinearLayout.viewPager(
    quickView: QuickView,
    withState: Boolean = true,
    defaultSize: Int = -1,
    limit: Int = 0,
    layoutParams: ViewGroup.LayoutParams = wrapContentHeightParams,
    noinline builder: (QuickViewPager.() -> Unit)? = null,
) {
    this.forceTo<ViewGroup>()
        .viewPager<T>(quickView, withState, defaultSize, limit, layoutParams, builder)
}

inline fun <reified T : Fragment> ViewGroup.viewPager(
    quickView: QuickView,
    withState: Boolean = true,
    defaultSize: Int = 1,
    limit: Int = 0,
    layoutParams: ViewGroup.LayoutParams = matchWrapLayoutParams,
    noinline builder: (QuickViewPager.() -> Unit)? = null,
) {
    val viewPager = QuickViewPager(context)
    viewPager.id = R.id.quickViewPager
    if (withState) {
        viewPager.adapter = object : FragmentStatePagerAdapter(
            quickView.getFragmentManager(), BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
        ), ViewPagerSize {
            override fun getItem(i: Int): Fragment {
                try {
                    val fragment = getFragment(
                        quickView, T::class.java.getDeclaredConstructor().newInstance().forceTo(), i
                    )
                    return fragment as Fragment
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                return null!!
            }

            override fun getCount(): Int {
                return if (getPageSize() != -1) {
                    getPageSize()
                } else {
                    defaultSize
                }
            }

            private var pageSizeI = -1
            override fun getPageSize(): Int {
                return pageSizeI
            }

            override fun setPageSize(size: Int) {
                pageSizeI = size
            }

            override fun getAdapter() = this

            override fun getItemPosition(`object`: Any): Int {
                return (viewPager as? ViewPagerSize)?.getItemPosition(`object`) ?: POSITION_NONE
            }
        }
    } else {
        viewPager.adapter = object : FragmentPagerAdapter(
            quickView.getFragmentManager(), BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
        ), ViewPagerSize {
            override fun getItem(i: Int): Fragment {
                try {
                    val fragment = getFragment(
                        quickView, T::class.java.getDeclaredConstructor().newInstance().forceTo(), i
                    )
                    return fragment as Fragment
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                return null!!
            }

            override fun getCount(): Int {
                return if (getPageSize() != -1) {
                    getPageSize()
                } else {
                    defaultSize
                }
            }

            private var pageSizeI = -1
            override fun getPageSize(): Int {
                return pageSizeI
            }

            override fun setPageSize(size: Int) {
                pageSizeI = size
            }

            override fun getAdapter() = this

            override fun getItemPosition(`object`: Any): Int {
                return (viewPager as? ViewPagerSize)?.getItemPosition(`object`) ?: POSITION_NONE
            }
        }
    }
    if (limit != 0) {
        viewPager.offscreenPageLimit = limit
    }
    addView(viewPager, layoutParams)
    builder?.invoke(viewPager)
}

fun LinearLayout.viewPager(
    quickView: QuickView,
    fragments: List<Fragment>,
    withState: Boolean = true,
    limit: Int = 0,
    layoutParams: ViewGroup.LayoutParams = wrapContentHeightParams,
    builder: (QuickViewPager.() -> Unit)? = null,
) {
    this.forceTo<ViewGroup>()
        .viewPager(quickView, fragments, withState, limit, layoutParams, builder)
}

fun ViewGroup.viewPager(
    quickView: QuickView,
    fragments: List<Fragment>,
    withState: Boolean = true,
    limit: Int = 0,
    layoutParams: ViewGroup.LayoutParams = matchWrapLayoutParams,
    builder: (QuickViewPager.() -> Unit)? = null,
) {
    val viewPager = QuickViewPager(context)
    viewPager.id = R.id.quickViewPager
    if (withState) {
        viewPager.adapter = FragmentsStateAdapter(quickView.getFragmentManager(), fragments)
    } else {
        viewPager.adapter = FragmentsAdapter(quickView.getFragmentManager(), fragments)
    }
    if (limit != 0) {
        viewPager.offscreenPageLimit = limit
    }
    addView(viewPager, layoutParams)
    builder?.invoke(viewPager)
}

fun LinearLayout.viewPager2(
    quickView: QuickView,
    fragments: List<Fragment>,
    limit: Int = 0,
    layoutParams: ViewGroup.LayoutParams = wrapContentHeightParams,
    builder: (ViewPager2.() -> Unit)? = null,
) {
    this.forceTo<ViewGroup>().viewPager2(quickView, fragments, limit, layoutParams, builder)
}

fun ViewGroup.viewPager2(
    quickView: QuickView,
    fragments: List<Fragment>,
    limit: Int = 0,
    layoutParams: ViewGroup.LayoutParams = matchWrapLayoutParams,
    builder: (ViewPager2.() -> Unit)? = null,
) {
    val viewPager = ViewPager2(context)
    viewPager.id = R.id.quickViewPager
    viewPager.adapter =
        object : FragmentStateAdapter(quickView.getFragmentManager(), quickView.getLifecycle()),
            ViewPagerSize2 {
            override fun createFragment(position: Int): Fragment {
                return fragments[position]
            }

            override fun getItemCount(): Int {
                return if (getPageSize() != -1) {
                    getPageSize()
                } else {
                    fragments.size
                }
            }

            private var pageSizeI = -1
            override fun getPageSize(): Int {
                return pageSizeI
            }

            override fun setPageSize(size: Int) {
                pageSizeI = size
            }

            override fun getAdapter2(): FragmentStateAdapter {
                return this
            }
        }
    if (limit != 0) {
        viewPager.offscreenPageLimit = limit
    }
    addView(viewPager, layoutParams)
    builder?.invoke(viewPager)
}

inline fun <reified T : Fragment> LinearLayout.viewPager2(
    quickView: QuickView,
    defaultSize: Int = 1,
    limit: Int = 0,
    layoutParams: ViewGroup.LayoutParams = wrapContentHeightParams,
    noinline builder: (ViewPager2.() -> Unit)? = null,
) {
    this.forceTo<ViewGroup>().viewPager2<T>(quickView, defaultSize, limit, layoutParams, builder)
}

inline fun <reified T : Fragment> ViewGroup.viewPager2(
    quickView: QuickView,
    defaultSize: Int = 1,
    limit: Int = 0,
    layoutParams: ViewGroup.LayoutParams = matchWrapLayoutParams,
    noinline builder: (ViewPager2.() -> Unit)? = null,
) {
    val viewPager = ViewPager2(context)
    viewPager.id = R.id.quickViewPager
    viewPager.adapter = object : FragmentStateAdapter(
        quickView.getFragmentManager(), quickView.getLifecycle()
    ), ViewPagerSize2 {
        override fun createFragment(i: Int): Fragment {
            try {
                val fragment = getFragment(
                    quickView, T::class.java.getDeclaredConstructor().newInstance().forceTo(), i
                )
                return fragment as Fragment
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return null!!
        }

        override fun getItemCount(): Int {
            return if (getPageSize() != -1) {
                getPageSize()
            } else {
                defaultSize
            }
        }

        private var pageSizeI = -1
        override fun getPageSize(): Int {
            return pageSizeI
        }

        override fun setPageSize(size: Int) {
            pageSizeI = size
        }

        override fun getAdapter2(): FragmentStateAdapter {
            return this
        }
    }
    if (limit != 0) {
        viewPager.offscreenPageLimit = limit
    }
    addView(viewPager, layoutParams)
    builder?.invoke(viewPager)
}