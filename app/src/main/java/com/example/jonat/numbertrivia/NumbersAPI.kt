package com.example.jonat.numbertrivia

import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url

interface NumbersAPI {
    @GET
    fun getRandomNumber(@Url url: String): Deferred<Number>


    companion object {
        val BASE_URL = "http://numbersapi.com/random/trivia"
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    }