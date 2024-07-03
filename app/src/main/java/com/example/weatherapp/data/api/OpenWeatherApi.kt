package com.example.weatherapp.data.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object OpenWeatherApi {
    private val httpClient = OkHttpClient.Builder().addInterceptor(OpenWeatherInterceptor()).build()

    val instance: OpenWeatherService by lazy {
        val retrofit = Retrofit.Builder().client(httpClient).baseUrl("https://api.openweathermap.org/").addConverterFactory(GsonConverterFactory.create()).build()

        retrofit.create(OpenWeatherService::class.java)
    }
}