package com.itis.androidsemfour.data.repository

import com.itis.androidsemfour.BuildConfig
import com.itis.androidsemfour.data.response.WeatherResponse
import com.itis.androidsemfour.data.api.Api
import com.itis.androidsemfour.data.response.City
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

class WeatherRepository {
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

    private val api: Api by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okhttp)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(Api::class.java)
    }

    suspend fun getWeather(cityName: String): WeatherResponse {
        return api.getWeatherByName(cityName)
    }

    suspend fun getWeather(cityId: Int): WeatherResponse {
        return api.getWeatherById(cityId)
    }

    suspend fun getCities(lat: Double, lon: Double, cnt: Int): List<City> {
        val cntString = cnt.toString()
        return api.getCities(lat, lon, cntString).list
    }

}
