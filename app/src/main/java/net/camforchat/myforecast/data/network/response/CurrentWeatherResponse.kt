package net.camforchat.myforecast.data.network.response

import com.google.gson.annotations.SerializedName
import net.camforchat.myforecast.data.db.entity.*

data class CurrentWeatherResponse(
    val base: String,
    val clouds: Clouds,
    val cod: Int,
    @SerializedName("coord")
    val geopoint: Geopoint,
    val dt: Int,
    val id: Int,
    val main: Main,
    val name: String,
    val sys: Sys,
    val timezone: Int,
    val visibility: Int,
    val weather: List<Weather>,
    val wind: Wind
)