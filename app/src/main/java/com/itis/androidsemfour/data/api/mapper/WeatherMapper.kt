package com.itis.androidsemfour.data.api.mapper

import com.itis.androidsemfour.data.response.WeatherResponse
import com.itis.androidsemfour.domain.entity.WeatherEntity

class WeatherMapper {

    fun mapToWeatherEntity(response: WeatherResponse): WeatherEntity =
        WeatherEntity(
            id = response.id,
            name = response.name,
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
            windDeg = response.wind.deg
        )
}
