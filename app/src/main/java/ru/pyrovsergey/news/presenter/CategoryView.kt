package ru.pyrovsergey.news.presenter

import com.arellomobile.mvp.MvpView

interface CategoryView: MvpView {
    fun showMessage(message: String)
    fun updateListArticles(page: Int)
}