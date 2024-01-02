package com.example.project004

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiInterface{

    @GET("en/{word}")
    suspend fun dictcall(@Path("word") word:String):Response<List<DictmodelItem>>
}