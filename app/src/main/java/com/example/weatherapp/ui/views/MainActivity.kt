package com.example.weatherapp.ui.views

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.weatherapp.R
import com.example.weatherapp.data.api.OpenWeatherApi
import com.example.weatherapp.databinding.ActivityMainBinding
import com.example.weatherapp.ui.viewmodels.MainActivityViewModel
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        updateUI()

        binding.mainTextInputLayout.setEndIconOnClickListener {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(binding.mainTvCityName.windowToken, 0)

            val cityName = binding.mainTextField.text.toString()
            viewModel.updateWeatherByCityName(cityName)
            updateUI()
        }
    }

    private fun updateUI() {
        binding.mainTextField.setText("")
        binding.mainTvCityName.text = viewModel.cityName
        binding.mainTvCityFeelsTemp.text = "${viewModel.feelsTemp}"
        binding.mainTvCityHumidity.text = "${viewModel.humidity}"
        binding.mainTvCityMinTemp.text = "${viewModel.minTemp}"
        binding.mainTvCityMaxTemp.text = "${viewModel.maxTemp}"
        binding.mainForecast01Date.text = "${viewModel.forecast01Date}"
        binding.mainForecast02Date.text = "${viewModel.forecast02Date}"
        binding.mainForecast01Temp.text = "${viewModel.forecast01Temp}"
        binding.mainForecast02Temp.text = "${viewModel.forecast02Temp}"
        binding.mainClForecast.visibility = if(viewModel.isForecastContainerVisible) View.VISIBLE else View.INVISIBLE
    }
}