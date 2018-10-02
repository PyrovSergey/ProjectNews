package ru.pyrovsergey.news.presenter

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.arellomobile.mvp.MvpView
import ru.pyrovsergey.news.di.App
import ru.pyrovsergey.news.fragments.NetworkDataNewsListener
import ru.pyrovsergey.news.model.dto.ArticlesItem

@InjectViewState
class NewsPresenter : MvpPresenter<NewsView>(), NetworkDataNewsListener {
    private val networkData = App.component.getNetworkData()
    private val repository = App.component.getRepository()

    fun getDataTopLinesNews() {
        networkData.getTopLinesNews(this)
    }

    override fun onSuccess(list: List<ArticlesItem>) {
        repository.listHeadlinesNews = list
        viewState.updateListArticles()
    }

    override fun onError(error: String) {
        viewState.showErrorMessage(error)
    }

    fun getTopHeadlinesArticles(): List<ArticlesItem> {
        return repository.listHeadlinesNews
    }
}

interface NewsView : MvpView {
    fun showErrorMessage(error: String)
    fun updateListArticles()
}