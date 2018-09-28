package ru.pyrovsergey.news.fragments

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import ru.pyrovsergey.news.R
import ru.pyrovsergey.news.di.App

class CategoryFragmentPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    private val PAGE_COUNT = 6

    override fun getCount(): Int {
        return PAGE_COUNT
    }

    override fun getItem(position: Int): Fragment {
        return CategoryPageFragment.newInstance(position + 1)
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return App.context.resources.getStringArray(R.array.tab_array)[position]
    }
}