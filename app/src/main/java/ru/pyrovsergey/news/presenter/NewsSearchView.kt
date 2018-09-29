package ru.pyrovsergey.news.presenter

import com.arellomobile.mvp.MvpView

interface NewsSearchView : MvpView {
    fun showMessage(message: String)
    fun updateFoundArticles()
}