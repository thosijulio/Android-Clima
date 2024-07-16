package com.example.weatherapp.ui.views

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.weatherapp.R
import com.example.weatherapp.data.api.OpenWeatherApi
import com.example.weatherapp.databinding.ActivityMainBinding
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    val openWeatherServiceApi = OpenWeatherApi.instance

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.mainTextInputLayout.setEndIconOnClickListener {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(binding.mainTvCityName.windowToken, 0)

            val cityName = binding.mainTextField.text.toString()

            CoroutineScope(Dispatchers.IO).launch {
                val getLocationResponse = openWeatherServiceApi.getLocation(cityName)

                if (getLocationResponse.isSuccessful) {
                    val getLocationResult = getLocationResponse.body()?.get(0)

                    val getWeatherResponse = openWeatherServiceApi.getCurrentWeatherData(
                        getLocationResult?.lat ?: 0.0, getLocationResult?.lon ?: 0.0)

                    val getForecastDailyResponse = openWeatherServiceApi.getForecastDaily(
                        getLocationResult?.lon ?: 0.0, getLocationResult?.lat ?: 0.0)

                    Log.i("Forecast", getForecastDailyResponse.toString())
                    if (getWeatherResponse.isSuccessful && getForecastDailyResponse.isSuccessful) {
                        val getWeatherResult = getWeatherResponse.body()
                        val getForecastResult = getForecastDailyResponse.body()

                        Log.i("Forecast", getForecastResult.toString())
                        withContext(Dispatchers.Main) {
                            binding.mainTvCityName.text = getLocationResult?.name
                            binding.mainTextField.setText("")
                            binding.mainTvCityFeelsTemp.text = getWeatherResult?.main?.feelsLike.toString()
                            binding.mainTvCityHumidity.text = getWeatherResult?.main?.humidity.toString()
                            binding.mainTvCityMinTemp.text = getWeatherResult?.main?.temp_min.toString()
                            binding.mainTvCityMaxTemp.text = getWeatherResult?.main?.temp_max.toString()
                        }
                    }
                }
            }
        }
    }
}