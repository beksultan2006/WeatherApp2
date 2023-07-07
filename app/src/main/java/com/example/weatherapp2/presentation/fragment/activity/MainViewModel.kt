package com.example.weatherapp2.presentation.fragment.activity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapp2.core.UIState
import com.example.weatherapp2.data.model.MainResponse
import com.example.weatherapp2.data.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
data class MainViewModel @Inject constructor(
    private val repository: WeatherRepository
): ViewModel() {

    var liveData: LiveData<UIState<MainResponse>> = MutableLiveData()

    fun getWeather(cityName: String) {
        liveData = repository.getWeather(cityName)
    }
}

