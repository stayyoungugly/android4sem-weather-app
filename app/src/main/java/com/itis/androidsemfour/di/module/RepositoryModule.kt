package com.itis.androidsemfour.di.module

import com.itis.androidsemfour.data.impl.WeatherRepositoryImpl
import com.itis.androidsemfour.domain.repository.WeatherRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @Binds
    fun weatherRepository(
        impl: WeatherRepositoryImpl
    ): WeatherRepository

}
