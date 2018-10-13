package ru.pyrovsergey.news.ui.fragments

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import ru.pyrovsergey.news.R
import ru.pyrovsergey.news.presenter.BookmarksPresenter
import ru.pyrovsergey.news.presenter.BookmarksView
import ru.pyrovsergey.news.ui.ArticlesFragmentAdapter

class FragmentBookmarks : MvpAppCompatFragment(), BookmarksView {

    @InjectPresenter
    lateinit var presenter: BookmarksPresenter

    private lateinit var manager: LinearLayoutManager
    private lateinit var recycler: RecyclerView
    private lateinit var adapter: ArticlesFragmentAdapter
    private lateinit var bookmarksTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    companion object {
        fun newInstance(): FragmentBookmarks {
            val fragmentBookmarks = FragmentBookmarks()
            val args = Bundle()
            fragmentBookmarks.arguments = args
            return fragmentBookmarks
        }
    }

    override fun onResume() {
        super.onResume()
        presenter.refreshBookmarksList()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_bookmarks, container, false)
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
        when (list.isEmpty()) {
            true -> showBackground()
            false -> hideBackground()
        }
        adapter.updateAdapter(list)
    }

    private fun showBackground() {
        bookmarksTextView.visibility = View.VISIBLE
    }

    private fun hideBackground() {
        bookmarksTextView.visibility = View.INVISIBLE
    }
}