package net.camforchat.myforecast.data.repository

import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import net.camforchat.myforecast.data.db.CurrentWeatherDao
import net.camforchat.myforecast.data.db.entity.CurrentWeatherEntry
import net.camforchat.myforecast.data.network.WeatherNetworkDataSource
import net.camforchat.myforecast.data.network.mapper.CurrentWeatherResponseToEntry
import net.camforchat.myforecast.data.network.response.CurrentWeatherResponse
import org.threeten.bp.ZonedDateTime
import java.util.*

class ForecastRepositoryImpl(
    private val currentWeatherDao: CurrentWeatherDao,
    private val weatherNetworkDataSource: WeatherNetworkDataSource
) : ForecastRepository {
    companion object {
        private const val DataTTLMinutes = 30L
    }

    init {
        weatherNetworkDataSource.downloadedCurrentWeather.observeForever { newCurrentWeather ->
            persistFetchedCurrentWeather(newCurrentWeather)
        }
    }

    override suspend fun getCurrentWeather(metric: Boolean): LiveData<CurrentWeatherEntry> {
        return withContext(Dispatchers.IO) {
            initWeatherData()
            return@withContext currentWeatherDao.getWeather()
        }
    }

    private fun persistFetchedCurrentWeather(fetchedWeather: CurrentWeatherResponse) {
        GlobalScope.launch(Dispatchers.IO) {
            currentWeatherDao.upsert(
                CurrentWeatherResponseToEntry.map(fetchedWeather)
            )
        }
    }

    private suspend fun initWeatherData() {
        if (isFetchCurrentNeeded(ZonedDateTime.now().minusHours(1)))
            fetchCurrentWeather()
    }

    private suspend fun fetchCurrentWeather() {
        weatherNetworkDataSource.fetchCurrentWeather(
            "Ekaterinburg",
            Locale.getDefault().language
        )
    }

    private fun isFetchCurrentNeeded(lastFetchTime: ZonedDateTime): Boolean {
        val ttlMinutesAgo = ZonedDateTime.now().minusMinutes(DataTTLMinutes)
        return lastFetchTime.isBefore(ttlMinutesAgo)
    }
}