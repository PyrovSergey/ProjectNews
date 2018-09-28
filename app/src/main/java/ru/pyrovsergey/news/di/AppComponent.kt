package ru.pyrovsergey.news.di

import dagger.Component
import ru.pyrovsergey.news.model.DataStorage
import ru.pyrovsergey.news.model.network.NetworkData
import javax.inject.Singleton

@Singleton
@Component(modules = [DataStorageModule::class, NetworkDataModule::class])
interface AppComponent {
    fun getDataStorage(): DataStorage
    fun getNetworkData(): NetworkData
}