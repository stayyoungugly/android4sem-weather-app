package com.itis.androidsemfour.di.module

import com.itis.androidsemfour.data.impl.WeatherRepositoryImpl
import com.itis.androidsemfour.domain.repository.WeatherRepository
import dagger.Binds
import dagger.Module

@Module
interface RepositoryModule {
    @Binds
    fun weatherRepository(
        impl: WeatherRepositoryImpl
    ): WeatherRepository

}
