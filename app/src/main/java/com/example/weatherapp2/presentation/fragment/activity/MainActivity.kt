package com.example.weatherapp2.presentation.fragment.activity

import android.annotation.SuppressLint
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherapp2.R
import com.example.weatherapp2.core.UIState
import com.example.weatherapp2.data.model.MainResponse
import com.example.weatherapp2.databinding.ActivityMainBinding
import com.example.weatherapp2.presentation.fragment.bottomSheet.FilterFragment
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), FilterFragment.Result {

    private lateinit var adapter: MainAdapter
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by lazy { ViewModelProvider(this)[MainViewModel::class.java] }
    private lateinit var runnable: Runnable
    private val handler = Handler(Looper.getMainLooper())
    private var city = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }
        if (city.isEmpty()) {
            city = "Bishkek"
        }

        initWeather()

        binding.rvActivity.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        adapter = MainAdapter()
        binding.rvActivity.adapter = adapter

        binding.btn0.setOnClickListener {
            FilterFragment(this).show(this.supportFragmentManager, "")
        }
    }

    private fun initWeather() {
        viewModel.liveData.observe(this) {
            when (it) {
                is UIState.Error -> this.showToast(it.error)
                is UIState.Loading -> this.showToast("Loading")
                is UIState.Success -> getWeather(it.data)
            }
        }

    }


    @SuppressLint("SetTextI18n")
    private fun getWeather(it: MainResponse) {
        Toast.makeText(this, it.location.name, Toast.LENGTH_SHORT).show()
        binding.textPros.text = "${it.current.humidity} %"
        binding.textMb.text = "${it.current.pressure_mb} mbar"
        binding.txtTime1.text = it.forecast.forecastday[0].astro.sunrise
        binding.txtTime2.text = it.forecast.forecastday[0].astro.sunset
        binding.txtTime3.text = it.forecast.forecastday[0].date
        binding.text33.text = "${it.current.tempC} "
        binding.btn0.text = it.location.name
        binding.textKm.text = "${it.current.wind_kph} km/h"
        binding.sunnyText.text = it.current.condition.text
        binding.text27.text = it.forecast.forecastday[0].day.mintemp_c.toString()
        binding.text35.text = it.forecast.forecastday[0].day.maxtemp_c.toString()
        adapter.addList(it.forecast.forecastday)

    }


    override fun click(name: String) {
        viewModel.getWeather(name)
        initWeather()
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun startUpdatingTime() {
        val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy | HH:mm:ss")
        runnable = object : Runnable {
            override fun run() {
                val currentTime = LocalDateTime.now()
                val formattedTime = formatter.format(currentTime)
                updateTimeUI(formattedTime)
                handler.postDelayed(this, 1000)
            }
        }
        handler.post(runnable)
    }

    private fun updateTimeUI(time: String) {
        binding.textData.text = time
    }


    private fun stopUpdatingTime() {
        handler.removeCallbacks(runnable)
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(runnable)
    }



    @SuppressLint("NewApi")
    override fun onResume() {
        super.onResume()
        startUpdatingTime()
    }

    override fun onPause() {
        super.onPause()
        stopUpdatingTime()
    }
}