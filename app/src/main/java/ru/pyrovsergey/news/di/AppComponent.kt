package ru.pyrovsergey.news.di

import dagger.Component
import ru.pyrovsergey.news.model.db.Repository
import ru.pyrovsergey.news.model.network.NetworkData
import javax.inject.Singleton

@Singleton
@Component(modules = [RepositoryModule::class, NetworkDataModule::class])
interface AppComponent {
    fun getRepository(): Repository
    fun getNetworkData(): NetworkData
}