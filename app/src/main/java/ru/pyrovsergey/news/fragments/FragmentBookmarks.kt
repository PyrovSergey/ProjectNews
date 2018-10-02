package ru.pyrovsergey.news.fragments

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import kotlinx.android.synthetic.main.fragment_bookmarks.*
import ru.pyrovsergey.news.R
import ru.pyrovsergey.news.presenter.BookmarksPresenter
import ru.pyrovsergey.news.presenter.BookmarksView
import ru.pyrovsergey.news.ui.ArticlesFragmentAdapter

class FragmentBookmarks : MvpAppCompatFragment(), BookmarksView, SwipeRefreshLayout.OnRefreshListener {

    @InjectPresenter
    lateinit var presenter: BookmarksPresenter

    private lateinit var manager: LinearLayoutManager
    private lateinit var recycler: RecyclerView
    private lateinit var adapter: ArticlesFragmentAdapter
    private lateinit var swipe: SwipeRefreshLayout
    private lateinit var bookmarksTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        presenter.refreshBookmarksList()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_bookmarks, container, false)
        swipe = view.findViewById(R.id.bookmarks_swipe_refresh_layout)
        swipe.setOnRefreshListener(this)
        bookmarksTextView = view.findViewById(R.id.bookmarks_text_view)
        recycler = view.findViewById(R.id.bookmarks_recycler)
        recycler.setHasFixedSize(true)
        manager = LinearLayoutManager(context)
        recycler.layoutManager = manager
        adapter = ArticlesFragmentAdapter(presenter.getBookmarks())
        recycler.adapter = adapter
        updateBookmarksArticles()
        return view
    }

    override fun updateBookmarksArticles() {
        val list = presenter.getBookmarks()
        if (list.isEmpty()) {
            showBackground()
        } else {
            hideBackground()
        }
        adapter = ArticlesFragmentAdapter(list)
        adapter.notifyDataSetChanged()
        recycler.adapter = adapter
        swipe.isRefreshing = false
    }

    private fun showBackground() {
        bookmarksTextView.visibility = View.VISIBLE
    }

    private fun hideBackground() {
        bookmarksTextView.visibility = View.INVISIBLE
    }

    override fun onRefresh() {
        presenter.refreshBookmarksList()
    }
}