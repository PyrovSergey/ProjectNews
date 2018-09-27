package ru.pyrovsergey.news.model.network

import android.widget.Toast
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import ru.pyrovsergey.news.App
import java.util.*

class NetworkData {
    private var disposable: Disposable? = null

    private val googleApi by lazy {
        GoogleApi.create()
    }

    fun getTopLinesNews() {
        disposable = googleApi.getAllHeadlinesNews(getLocal(), 20, "")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { result -> Toast.makeText(App.context, result.articles!![0].toString(), Toast.LENGTH_LONG).show() },
                        { error -> Toast.makeText(App.context, error.message, Toast.LENGTH_LONG).show() }
                )
    }

    private fun getLocal() = Locale.getDefault().country

    private fun getLanguage() = Locale.getDefault().language


}