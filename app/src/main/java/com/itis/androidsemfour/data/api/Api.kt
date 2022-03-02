package com.itis.androidsemfour.data.api

import com.itis.androidsemfour.data.response.CityList
import com.itis.androidsemfour.data.response.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {
    @GET("weather?")
    suspend fun getWeatherByName(@Query("q") city: String): WeatherResponse

    @GET("weather?")
    suspend fun getWeatherById(@Query("id") id: Int): WeatherResponse

    @GET("find?")
    suspend fun getCities(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("cnt") cnt: String
    ): CityList
}
