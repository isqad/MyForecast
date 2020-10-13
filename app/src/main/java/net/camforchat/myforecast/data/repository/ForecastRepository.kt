package net.camforchat.myforecast.data.repository

import androidx.lifecycle.LiveData
import net.camforchat.myforecast.data.db.entity.CurrentWeatherEntry

interface ForecastRepository {
    suspend fun getCurrentWeather(metric: Boolean): LiveData<CurrentWeatherEntry>
}