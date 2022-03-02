package com.itis.androidsemfour.data.response

import com.google.gson.annotations.SerializedName

data class City(
    @SerializedName("clouds")
    val clouds: Clouds,
    @SerializedName("coordinate")
    val coordinate: Coordinate,
    @SerializedName("dt")
    val dt: Int,
    @SerializedName("id")
    val id: Int,
    @SerializedName("main")
    val main: Main,
    @SerializedName("name")
    val name: String,
    @SerializedName("rain")
    val rain: Any,
    @SerializedName("snow")
    val snow: Any,
    @SerializedName("sys")
    val sys: Sys,
    @SerializedName("weather")
    val weather: List<Weather>,
    @SerializedName("wind")
    val wind: Wind
)
