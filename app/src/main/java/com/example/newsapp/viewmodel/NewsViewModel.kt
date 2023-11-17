package com.example.newsapp.viewmodel

import androidx.lifecycle.ViewModel
import com.example.newsapp.networking.NewsApiNewsArticleSearchResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class NewsViewModel : ViewModel(){
    val value = MutableStateFlow<String?>(null)

    private val _articles = MutableStateFlow<List<NewsApiNewsArticleSearchResult?>>(emptyList())
    val articles: StateFlow<List<NewsApiNewsArticleSearchResult?>> get() = _articles

    fun updateArticles(newArticles: List<NewsApiNewsArticleSearchResult?>){
        _articles.value = newArticles
    }
}