package ru.pyrovsergey.news.fragments

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import ru.pyrovsergey.news.App
import ru.pyrovsergey.news.R
import ru.pyrovsergey.news.presenter.FragmentPresenter
import ru.pyrovsergey.news.presenter.FragmentView

class FragmentCategory : MvpAppCompatFragment(), FragmentView {

    @InjectPresenter
    lateinit var presenter: FragmentPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_categories, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewPager = view.findViewById<ViewPager>(R.id.viewpager)
        viewPager.adapter = CategoryFragmentPagerAdapter(childFragmentManager, App.context)

        val tabLayout = view.findViewById<TabLayout>(R.id.sliding_tabs)
        tabLayout.setupWithViewPager(viewPager)
    }
}