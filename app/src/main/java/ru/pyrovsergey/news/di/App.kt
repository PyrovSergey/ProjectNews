package ru.pyrovsergey.news.di

import android.annotation.SuppressLint
import android.app.Application
import android.arch.persistence.room.Room
import android.content.Context
import android.net.ConnectivityManager
import es.dmoral.toasty.Toasty
import ru.pyrovsergey.news.R
import ru.pyrovsergey.news.model.db.AppDatabase


class App : Application() {

    companion object {
        lateinit var component: AppComponent
        @SuppressLint("StaticFieldLeak")
        lateinit var instance: App
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
        lateinit var database: AppDatabase
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        instance = this
        component = DaggerAppComponent.create()
        database = Room.databaseBuilder(context, AppDatabase::class.java, "database").build()
    }

    private fun isInternetAvailable(): Boolean {
        val connectivityManager: ConnectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return connectivityManager.activeNetworkInfo != null
                && connectivityManager.activeNetworkInfo.isAvailable
                && connectivityManager.activeNetworkInfo.isConnected
    }

    fun checkInternetConnection(): Boolean {
        val check = isInternetAvailable()
        when (!check) {
            true -> Toasty.error(App.context, getString(R.string.no_internet_connection) +
                    "\n" + getString(R.string.check_connection_settings), 0, true).show()
        }
        return check
    }
}