package ru.pyrovsergey.news.model.db

import ru.pyrovsergey.news.model.dto.ArticlesItem

interface RepositoryListener {
    fun onSuccessDeleteBookmark()
    fun onErrorDeleteBookmark()
    fun onSuccessInsertBookmark()
    fun onErrorInsertBookmark()
    fun onSuccessRequestBookmarksList()
    fun positiveCheckResultBookmarks(articlesItem: ArticlesItem)
    fun negativeCheckResultBookmarks()
}