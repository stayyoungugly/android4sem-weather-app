package com.itis.androidsemfour.data.response


import com.google.gson.annotations.SerializedName

data class CityList(
    @SerializedName("cod")
    val cod: String,
    @SerializedName("count")
    val count: Int,
    @SerializedName("list")
    val list: List<City>,
    @SerializedName("message")
    val message: String
)
