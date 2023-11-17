package com.example.newsapp.networking

import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface NewsService {
    @GET("reports")
    suspend fun getNewsArticles(
        @Header("Authorization") apiKey: String,
        @Query("term") term: String,
    ): NewsApiNewsArticleSearchResults
}