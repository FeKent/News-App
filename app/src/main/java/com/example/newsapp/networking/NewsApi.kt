package com.example.newsapp.networking

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


object NewsApi {
    val service: NewsService

    init {
        val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()

        val retrofit = Retrofit.Builder().baseUrl("https://api.worldapi.com/")
            .addConverterFactory(MoshiConverterFactory.create(moshi)).build()

        service = retrofit.create(NewsService::class.java)
    }

}