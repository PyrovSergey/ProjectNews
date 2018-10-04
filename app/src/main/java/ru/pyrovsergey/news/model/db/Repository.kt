package ru.pyrovsergey.news.model.db

import android.annotation.SuppressLint
import io.reactivex.Completable
import io.reactivex.CompletableObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import ru.pyrovsergey.news.di.App
import ru.pyrovsergey.news.model.dto.ArticlesItem


class Repository {
    var listHeadlinesNews: List<ArticlesItem> = mutableListOf()
    var generalList: List<ArticlesItem> = mutableListOf()
    var entertainmentList: List<ArticlesItem> = mutableListOf()
    var sportsList: List<ArticlesItem> = mutableListOf()
    var technologyList: List<ArticlesItem> = mutableListOf()
    var healthList: List<ArticlesItem> = mutableListOf()
    var businessList: List<ArticlesItem> = mutableListOf()
    var foundArticlesList: List<ArticlesItem> = mutableListOf()
    var bookmarksArticlesList: List<ArticlesItem> = mutableListOf()

    private var bookmarkDao = App.database.bookmarksDao()

    @SuppressLint("CheckResult")
    private fun refreshBookmarksList() {
        bookmarkDao.getAllBookmarks()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { employees ->
                    bookmarksArticlesList = employees
                }
    }

    @SuppressLint("CheckResult")
    fun getAllBookmarksList(listener: RepositoryListener) {
        bookmarkDao.getAllBookmarks()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { employees ->
                    bookmarksArticlesList = employees
                    listener.onSuccessRequestBookmarksList()
                }
    }

    fun deleteBookmark(article: ArticlesItem, listener: RepositoryListener) {
        Completable.fromAction {
            bookmarkDao.delete(article)
        }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(object : CompletableObserver {
                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onComplete() {
                        listener.onSuccessDeleteBookmark()
                        refreshBookmarksList()
                    }

                    override fun onError(e: Throwable) {
                        listener.onErrorDeleteBookmark()
                    }
                })
    }

    fun insertBookmark(article: ArticlesItem, listener: RepositoryListener) {
        Completable.fromAction {
            bookmarkDao.insert(article)
        }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(object : CompletableObserver {
                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onComplete() {
                        listener.onSuccessInsertBookmark()
                        refreshBookmarksList()
                    }

                    override fun onError(e: Throwable) {
                        listener.onErrorInsertBookmark()
                    }
                })
    }

    @SuppressLint("CheckResult")
    fun checkBookmarks(url: String?, listener: RepositoryListener) {
        bookmarkDao.getArticles(url!!)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { articles ->
                    when(articles.isEmpty()) {
                        true -> listener.negativeCheckResultBookmarks()
                        false -> listener.positiveCheckResultBookmarks(articles[0])
                    }
                }
    }
}