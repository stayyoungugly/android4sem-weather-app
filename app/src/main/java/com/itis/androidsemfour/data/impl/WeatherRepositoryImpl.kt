package com.itis.androidsemfour.data.impl

import com.itis.androidsemfour.data.api.Api
import com.itis.androidsemfour.data.api.mapper.CityMapper
import com.itis.androidsemfour.data.api.mapper.WeatherMapper
import com.itis.androidsemfour.domain.repository.WeatherRepository
import com.itis.androidsemfour.domain.entity.CityEntity
import com.itis.androidsemfour.domain.entity.WeatherEntity

class WeatherRepositoryImpl(
    private val api: Api,
    private val weatherMapper: WeatherMapper,
    private val cityMapper: CityMapper
) : WeatherRepository {

    override suspend fun getWeatherByName(cityName: String): WeatherEntity =
        weatherMapper.mapToWeatherEntity(api.getWeatherByName(cityName))

    override suspend fun getWeatherById(cityId: Int): WeatherEntity =
        weatherMapper.mapToWeatherEntity(api.getWeatherById(cityId))

    override suspend fun getCities(lat: Double, lon: Double, cnt: Int): List<CityEntity> {
        val cntString = cnt.toString()
        return cityMapper.mapToListCityEntity(api.getCities(lat, lon, cntString).list)
    }
}
