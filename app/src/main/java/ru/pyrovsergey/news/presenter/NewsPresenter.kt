package ru.pyrovsergey.news.presenter

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import ru.pyrovsergey.news.di.App
import ru.pyrovsergey.news.fragments.NetworkDataNewsListener
import ru.pyrovsergey.news.model.dto.Model

@InjectViewState
class NewsPresenter : MvpPresenter<NewsView>(), NetworkDataNewsListener {
    private val networkData = App.component.getNetworkData()
    private val dataStorage = App.component.getDataStorage()

    fun getDataTopLinesNews() {
        networkData.getTopLinesNews(this)
    }

    override fun onSuccess(list: List<Model.ArticlesItem>) {
        dataStorage.listHeadlinesNews = list
        viewState.updateListArticles()
    }

    override fun onError(error: String) {
        viewState.showErrorMessage(error)
    }

    fun getTopHeadlinesArticles(): List<Model.ArticlesItem> {
        return dataStorage.listHeadlinesNews
    }
}