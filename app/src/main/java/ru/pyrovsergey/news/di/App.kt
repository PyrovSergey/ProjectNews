package ru.pyrovsergey.news.di

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.net.ConnectivityManager


class App : Application() {

    companion object {
        lateinit var component: AppComponent
        @SuppressLint("StaticFieldLeak")
        lateinit var instance: App
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        instance = this
        component = DaggerAppComponent.create()
    }

    fun isInternetAvailable(): Boolean {
        val connectivityManager: ConnectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return connectivityManager.activeNetworkInfo != null
                && connectivityManager.activeNetworkInfo.isAvailable
                && connectivityManager.activeNetworkInfo.isConnected
    }
}