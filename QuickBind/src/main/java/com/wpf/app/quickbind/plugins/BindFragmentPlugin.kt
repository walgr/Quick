package com.wpf.app.quickbind.plugins

import android.app.Activity
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.lifecycle.ViewModel
import androidx.viewpager.widget.ViewPager
import com.wpf.app.quickbind.annotations.BindFragment
import com.wpf.app.quickbind.interfaces.Bind
import com.wpf.app.quickbind.interfaces.BindBaseFragment
import com.wpf.app.quickbind.interfaces.BindViewModel
import com.wpf.app.quickbind.utils.getViewContext
import com.wpf.app.quickbind.viewpager.ViewPagerSize
import java.lang.reflect.Field

/**
 * Created by 王朋飞 on 2022/7/13.
 *
 */
class BindFragmentPlugin : BasePlugin {

    override fun dealField(
        obj: Any,
        viewModel: ViewModel?,
        field: Field
    ): Boolean {
        try {
            val bindFragmentAnn: BindFragment = field.getAnnotation(BindFragment::class.java)
                ?: return false
            field.isAccessible = true
            val viewPagerObj = field[getRealObj(obj, viewModel)]
            if (viewPagerObj is ViewPager) {
                val viewPager: ViewPager = viewPagerObj
                if (bindFragmentAnn.limit > 0) {
                    viewPager.offscreenPageLimit = bindFragmentAnn.limit
                }
                var fragmentManager: FragmentManager? = null
                var context = obj
                if (obj is Bind) {
                    context = obj.getView().context
                }
                if (context is AppCompatActivity) {
                    fragmentManager = context.supportFragmentManager
                } else if (context is Fragment) {
                    fragmentManager = context.childFragmentManager
                }
                if (fragmentManager == null) return true
                if (bindFragmentAnn.withState) {
                    viewPager.adapter = object : FragmentStatePagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
                        private val mBaseFragmentList: HashMap<Int, BindBaseFragment> = HashMap()

                        override fun notifyDataSetChanged() {
                            super.notifyDataSetChanged()
                            mBaseFragmentList.clear()
                        }

                        override fun getItem(i: Int): Fragment {
                            try {
                                val baseFragment: BindBaseFragment =
                                    bindFragmentAnn.fragment.java.newInstance() as BindBaseFragment
                                if (obj is View) {
                                    val viewContext = obj.getViewContext()
                                    if (viewContext is BindViewModel<*>) {
                                        baseFragment.arguments = baseFragment.getInitBundle(viewContext, i)
                                    } else {
                                        if (viewContext is Activity) {
                                            baseFragment.arguments =
                                                baseFragment.getInitBundle(viewContext, i)
                                        }
                                        if (viewContext is Fragment) {
                                            baseFragment.arguments =
                                                baseFragment.getInitBundle(viewContext, i)
                                        }
                                    }
                                } else {
                                    if (viewModel != null) {
                                        baseFragment.arguments =
                                            baseFragment.getInitBundle(obj as BindViewModel<*>, i)
                                    } else {
                                        if (obj is Activity) {
                                            baseFragment.arguments =
                                                baseFragment.getInitBundle(obj, i)
                                        }
                                        if (obj is Fragment) {
                                            baseFragment.arguments =
                                                baseFragment.getInitBundle(obj, i)
                                        }
                                    }
                                }
                                mBaseFragmentList[i] = baseFragment
                                return baseFragment
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                            return null!!
                        }

                        override fun destroyItem(
                            container: ViewGroup,
                            position: Int,
                            `object`: Any
                        ) {
                            super.destroyItem(container, position, `object`)
                            mBaseFragmentList.remove(position)
                        }

                        override fun getCount(): Int {
                            return (viewPager as? ViewPagerSize)?.getPageSize() ?: 0
                        }

                        override fun getItemPosition(`object`: Any): Int {
                            return POSITION_NONE
                        }

                        override fun getPageTitle(position: Int): CharSequence? {
                            return mBaseFragmentList[position]?.getTitle()
                        }
                    }
                } else {
                    viewPager.adapter = object : FragmentPagerAdapter(fragmentManager) {
                        private val mBaseFragmentList: HashMap<Int, BindBaseFragment> = HashMap()

                        override fun notifyDataSetChanged() {
                            super.notifyDataSetChanged()
                            mBaseFragmentList.clear()
                        }

                        override fun getItem(i: Int): Fragment {
                            try {
                                val baseFragment: BindBaseFragment =
                                    bindFragmentAnn.fragment.java.newInstance() as BindBaseFragment
                                if (obj is View) {
                                    val viewContext = obj.getViewContext()
                                    if (viewContext is BindViewModel<*>) {
                                        baseFragment.arguments = baseFragment.getInitBundle(viewContext, i)
                                    } else {
                                        if (viewContext is Activity) {
                                            baseFragment.arguments =
                                                baseFragment.getInitBundle(viewContext, i)
                                        }
                                        if (viewContext is Fragment) {
                                            baseFragment.arguments =
                                                baseFragment.getInitBundle(viewContext, i)
                                        }
                                    }
                                } else {
                                    if (viewModel != null) {
                                        baseFragment.arguments =
                                            baseFragment.getInitBundle(obj as BindViewModel<*>, i)
                                    } else {
                                        if (obj is Activity) {
                                            baseFragment.arguments =
                                                baseFragment.getInitBundle(obj, i)
                                        }
                                        if (obj is Fragment) {
                                            baseFragment.arguments =
                                                baseFragment.getInitBundle(obj, i)
                                        }
                                    }
                                }
                                mBaseFragmentList[i] = baseFragment
                                return baseFragment
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                            return null!!
                        }

                        override fun destroyItem(
                            container: ViewGroup,
                            position: Int,
                            `object`: Any
                        ) {
                            super.destroyItem(container, position, `object`)
                            mBaseFragmentList.remove(position)
                        }

                        override fun getCount(): Int {
                            return (viewPager as? ViewPagerSize)?.getPageSize() ?: 0
                        }

                        override fun getItemPosition(`object`: Any): Int {
                            return POSITION_NONE
                        }

                        override fun getPageTitle(position: Int): CharSequence? {
                            return mBaseFragmentList[position]?.getTitle()
                        }
                    }
                }
            }
            return true
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return false
    }
}