package ru.pyrovsergey.news.model.network

import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import ru.pyrovsergey.news.model.dto.Articles

interface GoogleApi {
    @GET("/v2/top-headlines")
    fun getAllHeadlinesNews(@Query("country") country: String,
                            @Query("pageSize") pageSize: Int,
                            @Query("apiKey") key: String): Observable<Articles>

    @GET("/v2/top-headlines")
    fun getInCategoryHeadlinesNews(@Query("country") country: String,
                                   @Query("pageSize") pageSize: Int,
                                   @Query("category") category: String,
                                   @Query("apiKey") key: String): Observable<Articles>

    @GET("/v2/everything")
    fun getSearchNews(@Query("q") query: String,
                      @Query("pageSize") pageSize: Int,
                      @Query("sortBy") sort: String,
                      @Query("apiKey") key: String): Observable<Articles>


    companion object {
        fun create(): GoogleApi {
            val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl("https://newsapi.org")
                    .build()
            return retrofit.create(GoogleApi::class.java)
        }
    }
}