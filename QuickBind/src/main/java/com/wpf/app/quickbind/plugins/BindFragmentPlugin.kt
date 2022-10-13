package com.wpf.app.quickbind.plugins

import android.app.Activity
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
                    obj.getView()?.let {
                        context = it.context
                    }
                }
                if (context is AppCompatActivity) {
                    fragmentManager = (context as AppCompatActivity).supportFragmentManager
                } else if (context is Fragment) {
                    fragmentManager = (context as Fragment).childFragmentManager
                }
                if (fragmentManager == null) return true
                if (bindFragmentAnn.withState) {
                    viewPager.adapter = object : FragmentStatePagerAdapter(
                        fragmentManager,
                        BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
                    ) {
                        private val mBaseFragmentList: HashMap<Int, BindBaseFragment> = HashMap()

                        override fun notifyDataSetChanged() {
                            super.notifyDataSetChanged()
                            mBaseFragmentList.clear()
                        }

                        override fun getItem(i: Int): Fragment {
                            try {
                                val baseFragment: BindBaseFragment =
                                    bindFragmentAnn.fragment.java.newInstance() as BindBaseFragment
                                if (obj is Bind) {
                                    val bundle = obj.bindData(i)
                                    if (bundle != null) {
                                        (baseFragment as Fragment).arguments = bundle
                                    } else {
                                        val viewContext =
                                            (viewPager as? ViewPagerSize)?.currentContext()
                                                ?: obj.getView()?.getViewContext()
                                        if (viewContext is BindViewModel<*>) {
                                            (baseFragment as Fragment).arguments =
                                                baseFragment.getInitBundle(viewContext, i)
                                        } else {
                                            if (viewContext is Activity) {
                                                (baseFragment as Fragment).arguments =
                                                    baseFragment.getInitBundle(viewContext, i)
                                            }
                                            if (viewContext is Fragment) {
                                                (baseFragment as Fragment).arguments =
                                                    baseFragment.getInitBundle(viewContext, i)
                                            }
                                        }
                                    }
                                } else {
                                    if (viewModel != null) {
                                        (baseFragment as Fragment).arguments =
                                            baseFragment.getInitBundle(obj as BindViewModel<*>, i)
                                    } else {
                                        if (obj is Activity) {
                                            (baseFragment as Fragment).arguments =
                                                baseFragment.getInitBundle(obj, i)
                                        }
                                        if (obj is Fragment) {
                                            (baseFragment as Fragment).arguments =
                                                baseFragment.getInitBundle(obj, i)
                                        }
                                    }
                                }
                                mBaseFragmentList[i] = baseFragment
                                return baseFragment as Fragment
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
                            return (viewPager as? ViewPagerSize)?.getPageSize() ?: bindFragmentAnn.defaultSize
                        }

                        override fun getItemPosition(`object`: Any): Int {
                            return (viewPager as? ViewPagerSize)?.getItemPosition(`object`) ?: POSITION_NONE
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
                                if (obj is Bind) {
                                    val bundle = obj.bindData(i)
                                    if (bundle != null) {
                                        (baseFragment as Fragment).arguments = bundle
                                    } else {
                                        val viewContext =
                                            (viewPager as? ViewPagerSize)?.currentContext()
                                                ?: obj.getView()?.getViewContext()
                                        if (viewContext is BindViewModel<*>) {
                                            (baseFragment as Fragment).arguments =
                                                baseFragment.getInitBundle(viewContext, i)
                                        } else {
                                            if (viewContext is Activity) {
                                                (baseFragment as Fragment).arguments =
                                                    baseFragment.getInitBundle(viewContext, i)
                                            }
                                            if (viewContext is Fragment) {
                                                (baseFragment as Fragment).arguments =
                                                    baseFragment.getInitBundle(viewContext, i)
                                            }
                                        }
                                    }
                                } else {
                                    if (viewModel != null) {
                                        (baseFragment as Fragment).arguments =
                                            baseFragment.getInitBundle(obj as BindViewModel<*>, i)
                                    } else {
                                        if (obj is Activity) {
                                            (baseFragment as Fragment).arguments =
                                                baseFragment.getInitBundle(obj, i)
                                        }
                                        if (obj is Fragment) {
                                            (baseFragment as Fragment).arguments =
                                                baseFragment.getInitBundle(obj, i)
                                        }
                                    }
                                }
                                mBaseFragmentList[i] = baseFragment
                                return (baseFragment as Fragment)
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
                            return (viewPager as? ViewPagerSize)?.getPageSize() ?: bindFragmentAnn.defaultSize
                        }

                        override fun getItemPosition(`object`: Any): Int {
                            return (viewPager as? ViewPagerSize)?.getItemPosition(`object`) ?: POSITION_NONE
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