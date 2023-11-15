package com.example.newsapp.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class NewsViewModel : ViewModel(){
    val value = MutableStateFlow<String?>(null)
}