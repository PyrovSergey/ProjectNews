package ru.pyrovsergey.news.fragments

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import kotlinx.android.synthetic.main.fragment_search.*
import ru.pyrovsergey.news.ArticlesFragmentAdapter
import ru.pyrovsergey.news.R
import ru.pyrovsergey.news.presenter.NewsSearchPresenter
import ru.pyrovsergey.news.presenter.NewsSearchView

class FragmentNewsSearch : MvpAppCompatFragment(), NewsSearchView {

    @InjectPresenter
    lateinit var presenter: NewsSearchPresenter
    lateinit var searchRecycler: RecyclerView

    companion object {
        private const val key = "ru.pyrovsergey.news.fragments.query"
        fun newInstance(query: String): FragmentNewsSearch {
            val fragmentSearch = FragmentNewsSearch()
            val args = Bundle()
            args.putString(key, query)
            fragmentSearch.arguments = args
            return fragmentSearch
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.searchQuery(arguments!!.getString(key))
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_search, container, false)
        searchRecycler = view.findViewById(R.id.search_recycler)
        searchRecycler.setHasFixedSize(true)
        searchRecycler.layoutManager = LinearLayoutManager(context)
        searchRecycler.adapter = ArticlesFragmentAdapter(presenter.getFoundArticles())
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun updateFoundArticles() {
        textViewSearch.visibility = View.INVISIBLE
        searchRecycler.adapter = ArticlesFragmentAdapter(presenter.getFoundArticles())
    }


    override fun showMessage(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}