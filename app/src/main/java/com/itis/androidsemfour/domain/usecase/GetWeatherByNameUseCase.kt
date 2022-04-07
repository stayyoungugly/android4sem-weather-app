package com.itis.androidsemfour.domain.usecase

import com.itis.androidsemfour.domain.entity.WeatherEntity
import com.itis.androidsemfour.domain.repository.WeatherRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetWeatherByNameUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Main
) {

    suspend operator fun invoke(city: String): WeatherEntity {
        return withContext(dispatcher) {
            weatherRepository.getWeatherByName(city)
        }
    }
}
