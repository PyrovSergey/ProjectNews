package ru.pyrovsergey.news

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.net.ConnectivityManager


class App : Application() {
    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var instance: App
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        context = applicationContext
    }

    fun isInternetAvailable(): Boolean {
        val connectivityManager: ConnectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return connectivityManager.activeNetworkInfo != null
                && connectivityManager.activeNetworkInfo.isAvailable
                && connectivityManager.activeNetworkInfo.isConnected
    }
}