package ru.pyrovsergey.news.ui.fragments

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import ru.pyrovsergey.news.R
import ru.pyrovsergey.news.di.App
import ru.pyrovsergey.news.presenter.NewsPresenter
import ru.pyrovsergey.news.presenter.NewsView
import ru.pyrovsergey.news.ui.ArticlesFragmentAdapter

class FragmentNews : MvpAppCompatFragment(), NewsView, SwipeRefreshLayout.OnRefreshListener {

    @InjectPresenter
    lateinit var presenter: NewsPresenter
    private lateinit var manager: LinearLayoutManager
    private lateinit var recycler: RecyclerView
    private lateinit var adapter: ArticlesFragmentAdapter
    private lateinit var swipe: SwipeRefreshLayout

    companion object {
        fun newInstance(): FragmentNews {
            val fragmentHome = FragmentNews()
            val args = Bundle()
            fragmentHome.arguments = args
            return fragmentHome
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_news, container, false)
        swipe = view.findViewById(R.id.news_swipe_refresh_layout)
        swipe.setOnRefreshListener(this)
        recycler = view.findViewById(R.id.news_recycler)
        recycler.setHasFixedSize(true)
        manager = LinearLayoutManager(context)
        recycler.layoutManager = manager
        updateListArticles()
        return view
    }

    override fun onResume() {
        super.onResume()
        presenter.getDataTopLinesNews()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun updateListArticles() {
        adapter = ArticlesFragmentAdapter(presenter.getTopHeadlinesArticles())
        recycler.adapter = adapter
        swipe.isRefreshing = false
    }

    override fun onRefresh() {
        presenter.getDataTopLinesNews()
    }

    override fun showErrorMessage(error: String) {
        App.instance.checkInternetConnection()
        swipe.isRefreshing = false
    }
}