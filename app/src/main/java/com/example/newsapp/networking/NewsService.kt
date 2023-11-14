package com.example.newsapp.networking

import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface NewsService {
    @GET("report?")
    suspend fun getNewsArticles(
        @Header("Authorization") api_key: String,
        @Query("term") term: String,
    ): NewsApiNewsArticleSearchResult
}