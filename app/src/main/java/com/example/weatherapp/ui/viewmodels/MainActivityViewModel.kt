package com.example.weatherapp.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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

    private var _cityName = MutableLiveData("")
    val cityName get(): LiveData<String> = _cityName

    private var _temp = MutableLiveData(0.0)
    val temp: LiveData<Double>
        get() = _temp

    private var _maxTemp = MutableLiveData(0.0)
    val maxTemp: LiveData<Double>
        get() = _maxTemp

    private var _minTemp = MutableLiveData(0.0)
    val minTemp: LiveData<Double>
        get() = _minTemp

    private var _feelsTemp = MutableLiveData(0.0)
    val feelsTemp: LiveData<Double>
        get() = _feelsTemp

    private var _humidity = MutableLiveData(0.0)
    val humidity: LiveData<Double>
        get() = _humidity

    private var _isForecastContainerVisible = MutableLiveData(false)
    val isForecastContainerVisible: LiveData<Boolean>
        get() = _isForecastContainerVisible

    private var _forecast01Temp = MutableLiveData(0.0)
    val forecast01Temp: LiveData<Double>
        get() = _forecast01Temp

    private var _forecast01Date = MutableLiveData(0L)
    val forecast01Date: LiveData<Long>
        get() = _forecast01Date

    private var _forecast02Temp = MutableLiveData(0.0)
    val forecast02Temp: MutableLiveData<Double>
        get() = _forecast02Temp

    private var _forecast02Date = MutableLiveData(0L)
    val forecast02Date: LiveData<Long>
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
                _cityName.postValue(weatherData.name)
                _feelsTemp.postValue(weatherData.main.feelsLike)
                _temp.postValue(weatherData.main.temp)
                _minTemp.postValue(weatherData.main.temp_min)
                _maxTemp.postValue(weatherData.main.temp_max)
                _humidity.postValue(weatherData.main.humidity)
                _isForecastContainerVisible.postValue(true)
                _forecast01Date.postValue(forecastData.list[0].dt)
                _forecast01Temp.postValue(forecastData.list[0].main.temp)
                _forecast02Date.postValue(forecastData.list[1].dt)
                _forecast02Temp.postValue(forecastData.list[1].main.temp)
            }
        }
    }
}