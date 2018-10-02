package ru.pyrovsergey.news.fragments

import ru.pyrovsergey.news.model.dto.ArticlesItem


interface NetworkDataCategoryListeners {
    fun onSuccess(list: List<ArticlesItem>, category: Int)
    fun onError(error: String)
}