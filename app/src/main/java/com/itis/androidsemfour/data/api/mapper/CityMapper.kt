package com.itis.androidsemfour.data.api.mapper

import com.itis.androidsemfour.data.response.City
import com.itis.androidsemfour.domain.entity.CityEntity

class CityMapper {
    private fun mapToCityEntity(city: City): CityEntity =
        CityEntity(
            name = city.name,
            temp = city.main.temp,
            id = city.id
        )

    fun mapToListCityEntity(listCity: List<City>): List<CityEntity> {
        val listCityEntity = arrayListOf<CityEntity>()
        for (city in listCity) {
            listCityEntity.add(mapToCityEntity(city))
        }
        return listCityEntity
    }
}
