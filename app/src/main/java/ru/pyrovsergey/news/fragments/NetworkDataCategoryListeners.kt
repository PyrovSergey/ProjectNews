package ru.pyrovsergey.news.fragments

import ru.pyrovsergey.news.model.dto.Model

interface NetworkDataCategoryListeners {
    fun onSuccess(list: List<Model.ArticlesItem>, category: Int)
    fun onError(error: String)
}