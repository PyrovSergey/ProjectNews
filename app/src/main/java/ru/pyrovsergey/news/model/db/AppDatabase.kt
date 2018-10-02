package ru.pyrovsergey.news.model.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import ru.pyrovsergey.news.model.dto.ArticlesItem

@Database(entities = [ArticlesItem::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun bookmarksDao(): BookmarkDao
}