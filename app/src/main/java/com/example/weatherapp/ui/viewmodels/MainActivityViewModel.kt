package com.example.weatherapp.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.example.weatherapp.data.api.OpenWeatherApi
import com.example.weatherapp.data.api.OpenWeatherService
import com.example.weatherapp.data.model.CurrentWeatherData
import com.example.weatherapp.data.model.ForecastDailyData
import com.example.weatherapp.utils.formatTimestamp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivityViewModel : ViewModel() {
    private val mOpenWeatherService = OpenWeatherApi.instance
    private var _cityName: String = ""
    val cityName get() = _cityName

    private var _temp = 0.0
    val temp: Double
        get() = _temp

    private var _maxTemp = 0.0
    val maxTemp: Double
        get() = _maxTemp

    private var _minTemp = 0.0
    val minTemp: Double
        get() = _minTemp

    private var _feelsTemp = 0.0
    val feelsTemp: Double
        get() = _feelsTemp

    private var _humidity = 0.0
    val humidity: Double
        get() = _humidity

    private var _isForecastContainerVisible = false
    val isForecastContainerVisible: Boolean
        get() = _isForecastContainerVisible

    private var _forecast01Temp = 0.0
    val forecast01Temp: Double
        get() = _forecast01Temp

    private var _forecast01Date: Long = 0
    val forecast01Date: Long
        get() = _forecast01Date

    private var _forecast02Temp = 0.0
    val forecast02Temp: Double
        get() = _forecast02Temp

    private var _forecast02Date: Long = 0
    val forecast02Date: Long
        get() = _forecast02Date

    private suspend fun getCurrentWeatherData(cityName: String): CurrentWeatherData? {
        val currentWeatherDataResponse = mOpenWeatherService.getCurrentWeatherData(cityName)
        return currentWeatherDataResponse.body()
    }

    private suspend fun getForecastDailyData(cityName: String): ForecastDailyData? {
        val currentForecastResponse = mOpenWeatherService.getForecastDailyData(cityName)
        return currentForecastResponse.body()
    }

    fun updateWeatherByCityName(cityName: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val weatherData = getCurrentWeatherData(cityName)
            val forecastData = getForecastDailyData(cityName)

            if (weatherData != null && forecastData != null) {
                _cityName = weatherData.name
                _feelsTemp = weatherData.main.feelsLike
                _temp = weatherData.main.temp
                _minTemp = weatherData.main.temp_min
                _maxTemp = weatherData.main.temp_max
                _humidity = weatherData.main.humidity

                _isForecastContainerVisible = true
                _forecast01Date = forecastData.list[0].dt
                _forecast01Temp = forecastData.list[0].main.temp
                _forecast02Date = forecastData.list[1].dt
                _forecast02Temp = forecastData.list[1].main.temp
            }
        }
    }
}