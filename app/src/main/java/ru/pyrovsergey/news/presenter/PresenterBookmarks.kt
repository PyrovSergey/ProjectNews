package ru.pyrovsergey.news.presenter

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.arellomobile.mvp.MvpView
import ru.pyrovsergey.news.di.App
import ru.pyrovsergey.news.model.db.RepositoryListener
import ru.pyrovsergey.news.model.dto.ArticlesItem

@InjectViewState
class BookmarksPresenter : MvpPresenter<BookmarksView>(), RepositoryListener {

    private val repository = App.component.getRepository()

    fun getBookmarks(): List<ArticlesItem> {
        return repository.bookmarksArticlesList
    }

    fun refreshBookmarksList() {
        repository.getAllBookmarksList(this)
    }

    override fun onSuccessDeleteBookmark() {
        viewState.updateBookmarksArticles()
    }

    override fun onErrorDeleteBookmark() {
    }

    override fun onSuccessInsertBookmark() {
        viewState.updateBookmarksArticles()
    }

    override fun onErrorInsertBookmark() {
    }

    override fun onSuccessRequestBookmarksList() {
        viewState.updateBookmarksArticles()
    }

    override fun positiveCheckResultBookmarks(article: ArticlesItem) {
    }
}

interface BookmarksView : MvpView {
    fun updateBookmarksArticles()
}