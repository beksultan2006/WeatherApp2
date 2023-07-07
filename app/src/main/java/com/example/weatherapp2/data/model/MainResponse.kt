package com.example.weatherapp2.data.model

data class MainResponse(
    val current: Current,
    val id: Int,
    val forecast: Forecast,
    val location: Location
)
