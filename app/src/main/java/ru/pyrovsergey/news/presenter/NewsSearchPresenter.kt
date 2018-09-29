package ru.pyrovsergey.news.presenter

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import ru.pyrovsergey.news.di.App
import ru.pyrovsergey.news.fragments.NetworkDataNewsListener
import ru.pyrovsergey.news.model.dto.Model

@InjectViewState
class NewsSearchPresenter : MvpPresenter<NewsSearchView>(), NetworkDataNewsListener {

    private val networkData = App.component.getNetworkData()
    private val repository = App.component.getRepository()

    fun searchQuery(query: String) {
        networkData.getSearchArticles(query, this)
    }

    fun getFoundArticles(): List<Model.ArticlesItem> {
        return repository.foundArticlesList
    }

    override fun onSuccess(list: List<Model.ArticlesItem>) {
        repository.foundArticlesList = list
        viewState.updateFoundArticles()
    }

    override fun onError(error: String) {
        viewState.showMessage(error)
    }
}