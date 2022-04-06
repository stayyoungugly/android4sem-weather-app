package com.itis.androidsemfour.di

import com.itis.androidsemfour.BuildConfig
import com.itis.androidsemfour.data.api.Api
import com.itis.androidsemfour.data.api.mapper.CityMapper
import com.itis.androidsemfour.data.api.mapper.WeatherMapper
import com.itis.androidsemfour.data.impl.WeatherRepositoryImpl
import com.itis.androidsemfour.domain.repository.WeatherRepository
import com.itis.androidsemfour.domain.usecase.GetCitiesUseCase
import com.itis.androidsemfour.domain.usecase.GetWeatherByIdUseCase
import com.itis.androidsemfour.domain.usecase.GetWeatherByNameUseCase
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://api.openweathermap.org/data/2.5/"
private const val API_KEY = "3917f9e2c8cbd4b0359e5f15e9c21a89"
private const val QUERY_API_KEY = "appid"
private const val QUERY_UNITS = "units"
private const val UNITS = "metric"

object DIContainer {
    private val apiKeyInterceptor = Interceptor { chain ->
        val original = chain.request()
        val newURL = original.url.newBuilder()
            .addQueryParameter(QUERY_API_KEY, API_KEY)
            .build()

        chain.proceed(
            original.newBuilder()
                .url(newURL)
                .build()
        )
    }

    private val unitsInterceptor = Interceptor { chain ->
        val original = chain.request()
        val newURL: HttpUrl = original.url.newBuilder()
            .addQueryParameter(QUERY_UNITS, UNITS)
            .build()

        chain.proceed(
            original.newBuilder()
                .url(newURL)
                .build()
        )
    }

    private val okhttp: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(apiKeyInterceptor)
            .addInterceptor(unitsInterceptor)
            .also {
                if (BuildConfig.DEBUG) {
                    it.addInterceptor(
                        HttpLoggingInterceptor()
                            .setLevel(
                                HttpLoggingInterceptor.Level.BODY
                            )
                    )
                }
            }
            .build()
    }

    val api: Api by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okhttp)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(Api::class.java)
    }

    private val weatherRepository: WeatherRepository = WeatherRepositoryImpl(
        api = api,
        weatherMapper = WeatherMapper(),
        cityMapper = CityMapper()
    )
    val getCitiesUseCase: GetCitiesUseCase = GetCitiesUseCase(
        weatherRepository = weatherRepository
    )
    val getWeatherByNameUseCase: GetWeatherByNameUseCase = GetWeatherByNameUseCase(
        weatherRepository = weatherRepository
    )
    val getWeatherByIdUseCase: GetWeatherByIdUseCase = GetWeatherByIdUseCase(
        weatherRepository = weatherRepository
    )
}
