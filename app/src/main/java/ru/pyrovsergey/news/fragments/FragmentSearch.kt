package ru.pyrovsergey.news.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import ru.pyrovsergey.news.R
import ru.pyrovsergey.news.presenter.FragmentPresenter
import ru.pyrovsergey.news.presenter.FragmentView

class FragmentSearch: MvpAppCompatFragment(), FragmentView {
    @InjectPresenter
    lateinit var presenter: FragmentPresenter

    companion object {
        fun newInstance(): FragmentSearch {
            val fragmentSearch = FragmentSearch()
            val args = Bundle()
            fragmentSearch.arguments = args
            return fragmentSearch
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}