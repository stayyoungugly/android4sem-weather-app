package com.itis.androidsemfour.domain.repository

import com.itis.androidsemfour.data.response.City
import com.itis.androidsemfour.data.response.WeatherResponse

interface WeatherRepository {

    suspend fun getWeather(cityName: String): WeatherResponse

    suspend fun getWeather(cityId: Int): WeatherResponse

    suspend fun getCities(lat: Double, lon: Double, cnt: Int): List<City>
}
