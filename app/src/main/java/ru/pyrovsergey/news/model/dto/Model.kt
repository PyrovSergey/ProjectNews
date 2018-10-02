package ru.pyrovsergey.news.model.dto

import android.arch.persistence.room.*
import java.io.Serializable
import java.util.*

class Articles : Serializable {
    val totalResults: Int? = null
    val articles: List<ArticlesItem?>? = null
    val status: String? = null
}

@Entity(tableName = "bookmarks")
class ArticlesItem : Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var uid: Int = 0

    @ColumnInfo(name = "publishedAt")
    @TypeConverters(DateConverter::class)
    var publishedAt: Date? = null

    @ColumnInfo(name = "author")
    var author: String? = null

    @ColumnInfo(name = "urlToImage")
    var urlToImage: String? = null

    @ColumnInfo(name = "description")
    var description: String? = null

    @ColumnInfo(name = "source")
    @TypeConverters(SourceConverter::class)
    var source: Source? = null

    @ColumnInfo(name = "title")
    var title: String? = null

    @ColumnInfo(name = "url")
    var url: String? = null

    @ColumnInfo(name = "content")
    var content: String? = null
}

class Source : Serializable {
    var name: String? = null
}

class SourceConverter {
    companion object {
        @TypeConverter
        @JvmStatic
        fun fromSource(source: Source?): String? = source?.name

        @TypeConverter
        @JvmStatic
        fun toSource(name: String): Source? {
            val source = Source()
            source.name = name
            return source
        }
    }
}

class DateConverter {
    companion object {
        @TypeConverter
        @JvmStatic
        fun convertDateFrom(date: Date?): Long? = date?.time

        @TypeConverter
        @JvmStatic
        fun convertDateTo(date: Long?): Date? = date?.let(::Date)
    }
}
