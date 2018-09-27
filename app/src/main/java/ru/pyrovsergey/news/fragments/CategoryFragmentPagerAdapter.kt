package ru.pyrovsergey.news.fragments

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import ru.pyrovsergey.news.R

class CategoryFragmentPagerAdapter(fm: FragmentManager, private val context: Context) : FragmentPagerAdapter(fm) {
    private val PAGE_COUNT = 7

    override fun getCount(): Int {
        return PAGE_COUNT
    }

    override fun getItem(position: Int): Fragment {
        return CategoryPageFragment.newInstance(position + 1)
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getStringArray(R.array.tab_array)[position]
    }
}