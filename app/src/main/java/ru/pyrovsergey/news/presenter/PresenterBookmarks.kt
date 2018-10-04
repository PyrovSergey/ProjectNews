package ru.pyrovsergey.news.presenter

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.arellomobile.mvp.MvpView
import ru.pyrovsergey.news.di.App
import ru.pyrovsergey.news.model.db.BookmarksListener
import ru.pyrovsergey.news.model.dto.ArticlesItem

@InjectViewState
class BookmarksPresenter : MvpPresenter<BookmarksView>(), BookmarksListener {

    private val repository = App.component.getRepository()

    fun getBookmarks(): List<ArticlesItem> = repository.bookmarksArticlesList

    fun refreshBookmarksList() {
        repository.setChangeListener(this)
    }

    override fun update() {
        viewState.updateBookmarksArticles()
    }
}

interface BookmarksView : MvpView {
    fun updateBookmarksArticles()
}