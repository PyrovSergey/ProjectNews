package ru.pyrovsergey.news.presenter

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.arellomobile.mvp.MvpView
import ru.pyrovsergey.news.di.App
import ru.pyrovsergey.news.model.dto.ArticlesItem
import ru.pyrovsergey.news.ui.fragments.NetworkDataNewsListener

@InjectViewState
class NewsSearchPresenter : MvpPresenter<NewsSearchView>(), NetworkDataNewsListener {

    private val networkData = App.getInstance().getComponent().getNetworkData()
    private val repository = App.getInstance().getComponent().getRepository()

    fun searchQuery(query: String) {
        networkData.getSearchArticles(query, this)
    }

    fun getFoundArticles(): List<ArticlesItem> {
        return repository.foundArticlesList
    }

    override fun onSuccess(list: List<ArticlesItem>) {
        repository.foundArticlesList = list
        viewState.updateFoundArticles()
    }

    override fun onError(error: String) {
        viewState.showMessage(error)
    }
}

interface NewsSearchView : MvpView {
    fun showMessage(message: String)
    fun updateFoundArticles()
}