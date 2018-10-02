package ru.pyrovsergey.news.presenter

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.arellomobile.mvp.MvpView
import ru.pyrovsergey.news.di.App
import ru.pyrovsergey.news.model.db.RepositoryListener
import ru.pyrovsergey.news.model.dto.ArticlesItem

@InjectViewState
class WebPresenter : MvpPresenter<ViewWeb>(), RepositoryListener {

    private val repository = App.component.getRepository()

    fun addArticleToBookmarks(article: ArticlesItem) {
        repository.insertBookmark(article, this)
    }

    fun removeArticleInBookmarks(article: ArticlesItem) {
        repository.deleteBookmark(article, this)
    }

    override fun onSuccessDeleteBookmark() {
        viewState.showSuccessMessage("SuccessDeleteBookmark")
        viewState.negativeCheckBookmarks()
    }

    override fun onErrorDeleteBookmark() {
        viewState.showErrorMessage("ErrorDeleteBookmark")
    }

    override fun onSuccessInsertBookmark() {
        viewState.showSuccessMessage("Article added to bookmarks")
        viewState.updateBindObject()
    }

    override fun onErrorInsertBookmark() {
        viewState.showErrorMessage("ErrorInsertBookmark")
    }

    override fun positiveCheckResultBookmarks(article: ArticlesItem) {
        viewState.positiveCheckBookmarks(article)
    }

    fun checkArticle(url: String?): Any {
        return repository.isAddedToBookmarks(url, this)
    }

    override fun onSuccessRequestBookmarksList() {
    }
}

interface ViewWeb : MvpView {
    fun showSuccessMessage(message: String)
    fun showErrorMessage(message: String)
    fun positiveCheckBookmarks(article: ArticlesItem)
    fun updateBindObject()
    fun negativeCheckBookmarks()
}