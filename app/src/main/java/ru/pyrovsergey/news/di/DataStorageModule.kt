package ru.pyrovsergey.news.di

import dagger.Module
import dagger.Provides
import ru.pyrovsergey.news.model.DataStorage
import javax.inject.Singleton

@Module
class DataStorageModule {
    @Singleton
    @Provides
    fun provideDataStorage(): DataStorage {
        return DataStorage()
    }
}