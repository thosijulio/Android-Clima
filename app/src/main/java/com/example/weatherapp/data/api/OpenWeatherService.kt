package com.example.weatherapp.data.api

import com.example.weatherapp.data.model.CityLocationData
import com.example.weatherapp.data.model.CurrentWeatherData
import com.example.weatherapp.data.model.ForecastDaily
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherService {

    //    http://api.openweathermap.org/geo/1.0/direct?q=London&limit=5&appid={API key}
    @GET("geo/1.0/direct")
    suspend fun getLocation(
        @Query("q") cityName: String
    ): Response<List<CityLocationData>>

    // https://api.openweathermap.org/data/3.0/onecall?lat={lat}&lon={lon}&exclude={part}&appid={API key}
    @GET("data/2.5/weather")
    suspend fun getCurrentWeatherData(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("units") units: String = "metric",
    ): Response<CurrentWeatherData>
    // api.openweathermap.org/data/2.5/forecast/daily?lat={lat}&lon={lon}&cnt={cnt}&appid={API key}
    @GET("data/2.5/forecast")
    suspend fun getForecastDaily(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("units") units: String = "metric",
    ): Response<ForecastDaily>
}