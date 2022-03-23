package com.itis.androidsemfour.domain.repository

import com.itis.androidsemfour.domain.entity.CityEntity
import com.itis.androidsemfour.domain.entity.WeatherEntity

interface WeatherRepository {
    suspend fun getWeatherByName(cityName: String): WeatherEntity

    suspend fun getWeatherById(cityId: Int): WeatherEntity

    suspend fun getCities(lat: Double, lon: Double, cnt: Int): List<CityEntity>
}
