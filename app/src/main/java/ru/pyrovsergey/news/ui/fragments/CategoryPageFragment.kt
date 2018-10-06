package ru.pyrovsergey.news.ui.fragments

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import ru.pyrovsergey.news.R
import ru.pyrovsergey.news.di.App
import ru.pyrovsergey.news.model.dto.ArticlesItem
import ru.pyrovsergey.news.presenter.CategoryPresenter
import ru.pyrovsergey.news.presenter.CategoryView
import ru.pyrovsergey.news.ui.ArticlesFragmentAdapter

class CategoryPageFragment : MvpAppCompatFragment(), CategoryView {

    @InjectPresenter
    lateinit var presenterMove: CategoryPresenter

    private var page: Int = 0
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ArticlesFragmentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            page = arguments!!.getInt(ARG_PAGE)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        presenterMove.prepareContent(getCategory(page), page)
        recyclerView = inflater.inflate(R.layout.category_list, container, false) as RecyclerView
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(recyclerView.context)
        adapter = ArticlesFragmentAdapter(getItemPlaceList(page))
        recyclerView.adapter = adapter
        return recyclerView
    }

    private fun getItemPlaceList(page: Int): List<ArticlesItem> {
        return when (page) {
            1 -> presenterMove.getGeneralList()
            2 -> presenterMove.getEntertainmentList()
            3 -> presenterMove.getSportsList()
            4 -> presenterMove.getTechnologyList()
            5 -> presenterMove.getHealthList()
            6 -> presenterMove.getBusinessList()
            else -> presenterMove.getTopHeadlineList()
        }
    }

    private fun getCategory(position: Int): String {
        return App.context.resources.getStringArray(R.array.tab_array)[position - 1]
    }

    override fun updateListArticles(page: Int) {
        adapter.updateAdapter(getItemPlaceList(page))
        //recyclerView.adapter = ArticlesFragmentAdapter(getItemPlaceList(page))
    }

    companion object {

        const val ARG_PAGE = "ARG_PAGE"
        fun newInstance(page: Int): CategoryPageFragment {
            val bundle = Bundle()
            bundle.putInt(ARG_PAGE, page)
            val fragment = CategoryPageFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun showMessage(message: String) {
        App.instance.checkInternetConnection()
    }
}