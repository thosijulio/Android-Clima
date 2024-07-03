package com.example.weatherapp.data.api

import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Response

class OpenWeatherInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalUrl: HttpUrl = originalRequest.url

        val newUrl: HttpUrl = originalUrl.newBuilder().addQueryParameter("appid", "0b5d840886213a1a5eb78ed2743d4e47").build()
        val newRequest = originalRequest.newBuilder().url(newUrl).build()

        return chain.proceed(newRequest)
    }

}