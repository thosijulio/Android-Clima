package com.example.weatherapp.data.model

data class Temp(
    val temp: Double,
)

data class TempList(
    val dt: Long,
    val main: Temp,
)

data class ForecastDailyData(
    val list: List<TempList>
)