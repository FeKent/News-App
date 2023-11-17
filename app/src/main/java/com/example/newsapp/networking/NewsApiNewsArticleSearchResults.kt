package com.example.newsapp.networking

import com.squareup.moshi.Json

data class NewsApiNewsArticleSearchResults(
    @Json(name="reports")
    val results: List<NewsApiNewsArticleSearchResult?>
)
