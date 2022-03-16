package com.itis.androidsemfour.domain.entity

data class WeatherEntity(
    var id: Int,
    var name: String,
    var temp: Double,
    var tempMax: Double,
    var tempMin: Double,
    var humidity: Int,
    var pressure: Int,
    var speed: Double,
    var deg: Int,
    var description: String,
    var feelsLike: Double,
    var windSpeed: Double,
    var windDeg: Int,
    var icon: String
)
