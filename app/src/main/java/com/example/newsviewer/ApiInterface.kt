package com.example.newsviewer

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("v2/top-headlines")
    suspend fun getNews(

        @Query("country")
        country: String,

        @Query("apiKey")
        apiKey: String

    ): Response<NewsResponse>
}