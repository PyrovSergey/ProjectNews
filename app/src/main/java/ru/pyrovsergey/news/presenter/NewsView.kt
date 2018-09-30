package ru.pyrovsergey.news.presenter

import com.arellomobile.mvp.MvpView

interface NewsView : MvpView {
    fun showErrorMessage(error: String)
    fun updateListArticles()
}