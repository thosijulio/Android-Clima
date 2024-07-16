package com.example.weatherapp.data.model

import com.google.gson.annotations.SerializedName

data class Main(
    val temp: Double,
    @SerializedName("feels_like")
    val feelsLike: Double,
    val humidity: Double,
    val temp_min: Double,
    val temp_max: Double
)


data class CurrentWeatherData(
    val main: Main,
    val dt: Long,
    val name: String
)
