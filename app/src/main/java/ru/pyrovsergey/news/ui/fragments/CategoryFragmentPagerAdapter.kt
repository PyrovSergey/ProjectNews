package ru.pyrovsergey.news.ui.fragments

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import ru.pyrovsergey.news.R
import ru.pyrovsergey.news.di.App

class CategoryFragmentPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getCount(): Int {
        return PAGE_COUNT
    }

    override fun getItem(position: Int): Fragment {
        return CategoryPageFragment.newInstance(position + 1)
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return App.getInstance().getContext().resources.getStringArray(R.array.tab_array_title)[position]
    }

    companion object {
        private const val PAGE_COUNT = 6
    }
}