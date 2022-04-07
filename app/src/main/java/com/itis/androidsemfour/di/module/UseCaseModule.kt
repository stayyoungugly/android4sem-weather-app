package com.itis.androidsemfour.di.module

import com.itis.androidsemfour.domain.repository.WeatherRepository
import com.itis.androidsemfour.domain.usecase.GetCitiesUseCase
import com.itis.androidsemfour.domain.usecase.GetWeatherByIdUseCase
import com.itis.androidsemfour.domain.usecase.GetWeatherByNameUseCase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class UseCaseModule {
    @Provides
    @Singleton
    fun provideGetByNameUseCase(
        weatherRepository: WeatherRepository
    ) = GetWeatherByNameUseCase(weatherRepository)

    @Provides
    @Singleton
    fun provideGetByIdUseCase(
        weatherRepository: WeatherRepository
    ) = GetWeatherByIdUseCase(weatherRepository)

    @Provides
    @Singleton
    fun provideCitiesCase(
        weatherRepository: WeatherRepository
    ) = GetCitiesUseCase(weatherRepository)
}
