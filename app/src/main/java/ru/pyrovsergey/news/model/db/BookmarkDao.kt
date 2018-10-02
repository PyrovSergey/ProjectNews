package ru.pyrovsergey.news.model.db

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import io.reactivex.Maybe
import ru.pyrovsergey.news.model.dto.ArticlesItem


@Dao
interface BookmarkDao {
    @Query("SELECT * FROM bookmarks")
    fun getAllBookmarks(): Maybe<List<ArticlesItem>>

    @Query("SELECT * FROM bookmarks WHERE url = :url")
    fun getArticles(url: String): Maybe<ArticlesItem>

    @Insert
    fun insert(article: ArticlesItem)

    @Delete
    fun delete(article: ArticlesItem): Int
}