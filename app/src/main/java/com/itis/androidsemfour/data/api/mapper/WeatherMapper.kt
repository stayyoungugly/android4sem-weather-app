package com.itis.androidsemfour.data.api.mapper

import com.itis.androidsemfour.data.response.WeatherResponse
import com.itis.androidsemfour.domain.entity.WeatherEntity

class WeatherMapper {

    fun mapToWeatherEntity(response: WeatherResponse): WeatherEntity =
        WeatherEntity(
            id = response.id,
            cityName = response.name,
            temp = response.main.temp,
            tempMax = response.main.tempMax,
            tempMin = response.main.tempMin,
            humidity = response.main.humidity,
            pressure = response.main.pressure,
            speed = response.wind.speed,
            deg = response.wind.deg,
            description = response.weather[0].description,
            icon = response.weather[0].icon,
            feelsLike = response.main.feelsLike,
            windSpeed = response.wind.speed,
            windDeg = response.wind.deg,
            windDirection = when (response.wind.deg) {
                in 0..22 -> "North"
                in 23..67 -> "North-East"
                in 68..112 -> "East"
                in 113..157 -> "South-East"
                in 158..202 -> "South"
                in 203..247 -> "South-West"
                in 248..292 -> "West"
                in 293..337 -> "North-West"
                in 337..361 -> "North"
                else -> "Not found"
            }
        )
}
