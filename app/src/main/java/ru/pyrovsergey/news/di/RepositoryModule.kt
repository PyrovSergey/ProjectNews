package ru.pyrovsergey.news.di

import dagger.Module
import dagger.Provides
import ru.pyrovsergey.news.model.Repository
import javax.inject.Singleton

@Module
class RepositoryModule {
    @Singleton
    @Provides
    fun provideRepository(): Repository {
        return Repository()
    }
}