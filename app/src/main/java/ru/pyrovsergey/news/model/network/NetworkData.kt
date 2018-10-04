package ru.pyrovsergey.news.model.network

import android.annotation.SuppressLint
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.pyrovsergey.news.ui.fragments.NetworkDataCategoryListeners
import ru.pyrovsergey.news.ui.fragments.NetworkDataNewsListener
import ru.pyrovsergey.news.model.dto.ArticlesItem
import java.util.*

class NetworkData {

    private val googleApi by lazy {
        GoogleApi.create()
    }

    @SuppressLint("CheckResult")
    fun getTopLinesNews(newsListener: NetworkDataNewsListener) {
        googleApi.getAllHeadlinesNews(getLocal(), 100, KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { result -> newsListener.onSuccess(result.articles as List<ArticlesItem>) },
                        { error -> newsListener.onError(error.message!!) }
                )
    }

    @SuppressLint("CheckResult")
    fun getCategoryArticles(category: String, newsListener: NetworkDataCategoryListeners, page: Int) {
        googleApi.getInCategoryHeadlinesNews(getLocal(), 100, category, KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { result -> newsListener.onSuccess(result.articles as List<ArticlesItem>, page) },
                        { error -> newsListener.onError(error.message!!) }
                )
    }

    @SuppressLint("CheckResult")
    fun getSearchArticles(query: String, newsListener: NetworkDataNewsListener) {
        googleApi.getSearchNews(query, 100, RELEVANCY, KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { result -> newsListener.onSuccess(result.articles as List<ArticlesItem>) },
                        { error -> newsListener.onError(error.message!!) }
                )
    }

    private fun getLocal() = Locale.getDefault().country

    //private fun getLanguage() = Locale.getDefault().language

    companion object {
        const val KEY = ""
        const val RELEVANCY = "relevancy"
    }
}