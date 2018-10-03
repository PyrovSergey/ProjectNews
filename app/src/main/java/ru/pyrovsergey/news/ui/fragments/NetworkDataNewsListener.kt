package ru.pyrovsergey.news.ui.fragments

import ru.pyrovsergey.news.model.dto.ArticlesItem


interface NetworkDataNewsListener {
    fun onSuccess(list: List<ArticlesItem>)
    fun onError(error: String)
}