package ru.pyrovsergey.news.fragments

import ru.pyrovsergey.news.model.dto.ArticlesItem


interface NetworkDataNewsListener {
    fun onSuccess(list: List<ArticlesItem>)
    fun onError(error: String)
}