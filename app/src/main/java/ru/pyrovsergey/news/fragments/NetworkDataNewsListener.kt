package ru.pyrovsergey.news.fragments

import ru.pyrovsergey.news.model.dto.Model

interface NetworkDataNewsListener {
    fun onSuccess(list: List<Model.ArticlesItem>)
    fun onError(error: String)
}