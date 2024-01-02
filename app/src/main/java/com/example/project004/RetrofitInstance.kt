package com.example.project004

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val baseUrl="https://api.dictionaryapi.dev/api/v2/entries/"

    private fun getInstance(): Retrofit{
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val apiInterface:ApiInterface= getInstance().create(ApiInterface::class.java)
}