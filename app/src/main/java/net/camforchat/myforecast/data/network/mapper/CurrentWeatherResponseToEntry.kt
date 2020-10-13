package net.camforchat.myforecast.data.network.mapper

import net.camforchat.myforecast.data.db.entity.CurrentWeatherEntry
import net.camforchat.myforecast.data.network.response.CurrentWeatherResponse

object CurrentWeatherResponseToEntry {
    fun map(currentWeatherResponse: CurrentWeatherResponse): CurrentWeatherEntry {
        return CurrentWeatherEntry(
            base = currentWeatherResponse.base,
            clouds = currentWeatherResponse.clouds,
            cod = currentWeatherResponse.cod,
            geopoint = currentWeatherResponse.geopoint,
            dt = currentWeatherResponse.dt,
            main = currentWeatherResponse.main,
            name = currentWeatherResponse.name,
            sys = currentWeatherResponse.sys,
            timezone = currentWeatherResponse.timezone,
            visibility = currentWeatherResponse.visibility,
            wind = currentWeatherResponse.wind
        )
    }
}