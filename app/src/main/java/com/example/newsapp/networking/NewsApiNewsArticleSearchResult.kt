package com.example.newsapp.networking

import com.squareup.moshi.Json

data class NewsApiNewsArticleSearchResult(
    val claim: String?,
    val summary: String?,
    @Json (name="source_citation_url")
    val url: String?
)
