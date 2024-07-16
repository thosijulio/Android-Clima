package com.example.weatherapp.data.api

import com.example.weatherapp.data.model.CityLocationData
import com.example.weatherapp.data.model.CurrentWeatherData
import com.example.weatherapp.data.model.ForecastDailyData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherService {

    //    http://api.openweathermap.org/geo/1.0/direct?q=London&limit=5&appid={API key}
    @GET("geo/1.0/direct")
    suspend fun getLocation(
        @Query("q") cityName: String
    ): Response<List<CityLocationData>>

    // https://api.openweathermap.org/data/data/2.5/weather?lat={lat}&lon={lon}&exclude={part}&appid={API key}
    @GET("data/2.5/weather")
    suspend fun getCurrentWeatherData(
        @Query("q") cityName: String,
        @Query("units") units: String = "metric",
    ): Response<CurrentWeatherData>
    // api.openweathermap.org/data/2.5/forecast/daily?lat={lat}&lon={lon}&cnt={cnt}&appid={API key}
    @GET("data/2.5/forecast")
    suspend fun getForecastDailyData(
        @Query("q") cityName: String,
        @Query("units") units: String = "metric",
    ): Response<ForecastDailyData>
}