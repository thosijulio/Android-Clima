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
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    private val mTextInputLayout: TextInputLayout by lazy { findViewById(R.id.main_text_input_layout) }
    private val mTextField: TextInputEditText by lazy { findViewById(R.id.main_text_field) }
    private val mCityName: TextView by lazy { findViewById(R.id.main_tv_city_name) }
    private val mCityTemp: TextView by lazy { findViewById(R.id.main_tv_city_temp) }
    private val mCityMaxTemp: TextView by lazy { findViewById(R.id.main_tv_city_max_temp) }
    private val mCityMinTemp: TextView by lazy { findViewById(R.id.main_tv_city_min_temp) }
    private val mCityFeelsLike: TextView by lazy { findViewById(R.id.main_tv_city_feels_temp) }
    private val mCityHumidity: TextView by lazy { findViewById(R.id.main_tv_city_humidity) }
    private val mCityForecastContainer: ConstraintLayout by lazy { findViewById(R.id.main_cl_forecast) }
    private val mCityForecast01Temp: TextView by lazy { findViewById(R.id.main_forecast_01_temp) }
    private val mCityForecast01Date: TextView by lazy { findViewById(R.id.main_forecast_01_date) }
    private val mCityForecast02Temp: TextView by lazy { findViewById(R.id.main_forecast_02_temp) }
    private val mCityForecast02Date: TextView by lazy { findViewById(R.id.main_forecast_02_date) }

    val openWeatherServiceApi = OpenWeatherApi.instance

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mTextInputLayout.setEndIconOnClickListener {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(mCityName.windowToken, 0)

            val cityName = mTextField.text.toString()

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
                            mCityName.text = getLocationResult?.name
                            mTextField.setText("")
                            mCityFeelsLike.text = getWeatherResult?.main?.feelsLike.toString()
                            mCityHumidity.text = getWeatherResult?.main?.humidity.toString()
                            mCityMinTemp.text = getWeatherResult?.main?.temp_min.toString()
                            mCityMaxTemp.text = getWeatherResult?.main?.temp_max.toString()
                        }
                    }
                }
            }
        }
    }
}