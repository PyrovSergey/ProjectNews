package ru.pyrovsergey.news.model.dto

import java.io.Serializable
import java.util.*

object Model {
    data class Articles(
            val totalResults: Int? = null,
            val articles: List<ArticlesItem?>? = null,
            val status: String? = null
    ) : Serializable

    data class ArticlesItem(
            val publishedAt: Date? = null,
            val author: String? = null,
            val urlToImage: String? = null,
            val description: String? = null,
            val source: Source? = null,
            val title: String? = null,
            val url: String? = null,
            val content: String? = null
    ) : Serializable

    data class Source(
            val name: String? = null,
            val id: String? = null
    ) : Serializable
}