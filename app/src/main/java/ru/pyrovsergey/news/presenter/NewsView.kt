package ru.pyrovsergey.news.presenter

import com.arellomobile.mvp.MvpView
import ru.pyrovsergey.news.model.dto.Model

interface NewsView : MvpView {
    fun showErrorMessage(error: String)
    fun updateListArticles()
}