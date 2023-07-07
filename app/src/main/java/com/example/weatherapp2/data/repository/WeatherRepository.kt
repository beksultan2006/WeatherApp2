package com.example.weatherapp2.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.weatherapp2.core.UIState
import com.example.weatherapp2.data.model.MainResponse
import com.example.weatherapp2.data.service.WeatherService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject


class WeatherRepository @Inject constructor(
    private val apiService: WeatherService
) {

    fun getWeather(cityName: String): LiveData<UIState<MainResponse>> {
        val liveData = MutableLiveData<UIState<MainResponse>>()
        liveData.value = UIState.Loading()
        apiService.getCityWeatherData(cityName).enqueue(object : Callback<MainResponse> {
            override fun onResponse(
                call: Call<MainResponse>,
                response: Response<MainResponse>
            ) {
                if (response.isSuccessful) {
                    liveData.value = response.body()?.let { UIState.Success(it) }!!
                }
            }

            override fun onFailure(call: Call<MainResponse>, t: Throwable) {
                liveData.value = t.message?.let { UIState.Error(it) }
            }
        })
        return liveData
    }
}