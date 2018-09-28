package ru.pyrovsergey.news.di

import dagger.Module
import dagger.Provides
import ru.pyrovsergey.news.model.network.NetworkData
import javax.inject.Singleton

@Module
class NetworkDataModule {
    @Singleton
    @Provides
    fun provideNetworkData(): NetworkData {
        return NetworkData()
    }
}