package ru.pyrovsergey.news.fragments

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import ru.pyrovsergey.news.ArticlesFragmentAdapter
import ru.pyrovsergey.news.R
import ru.pyrovsergey.news.di.App
import ru.pyrovsergey.news.model.dto.Model
import ru.pyrovsergey.news.presenter.CategoryPresenter
import ru.pyrovsergey.news.presenter.CategoryView

class CategoryPageFragment : MvpAppCompatFragment(), CategoryView {

    @InjectPresenter
    lateinit var presenter: CategoryPresenter

    private var page: Int = 0
    lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            page = arguments!!.getInt(ARG_PAGE)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        presenter.prepareContent(getCategory(page), page)
        recyclerView = inflater.inflate(R.layout.category_list, container, false) as RecyclerView
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(recyclerView.context)
        recyclerView.adapter = ArticlesFragmentAdapter(getItemPlaceList(page))
        return recyclerView
    }

    private fun getItemPlaceList(page: Int): List<Model.ArticlesItem> {
        return when (page) {
            1 -> presenter.getGeneralList()
            2 -> presenter.getEntertainmentList()
            3 -> presenter.getSportsList()
            4 -> presenter.getTechnologyList()
            5 -> presenter.getHealthList()
            6 -> presenter.getBusinessList()
            else -> presenter.getTopHeadlineList()
        }
    }

    private fun getCategory(position: Int): String {
        return App.context.resources.getStringArray(R.array.tab_array)[position - 1]
    }

    override fun updateListArticles(page: Int) {
        recyclerView.adapter = ArticlesFragmentAdapter(getItemPlaceList(page))
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