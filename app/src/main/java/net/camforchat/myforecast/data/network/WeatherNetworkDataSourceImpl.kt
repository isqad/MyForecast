package net.camforchat.myforecast.data.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import net.camforchat.myforecast.data.OpenWeatherMapAPIService
import net.camforchat.myforecast.data.network.response.CurrentWeatherResponse
import net.camforchat.myforecast.internal.NoConnectivityException

class WeatherNetworkDataSourceImpl(
    private val currenWeatherAPIService: OpenWeatherMapAPIService
) : WeatherNetworkDataSource {
    private val _downloadedCurrentWeather = MutableLiveData<CurrentWeatherResponse>()
    override val downloadedCurrentWeather: LiveData<CurrentWeatherResponse>
        get() = _downloadedCurrentWeather

    override suspend fun fetchCurrentWeather(location: String, languageCode: String) {
        try {
            val fetchedCurrentWeather =
                currenWeatherAPIService.getCurrentWeather(location)
            _downloadedCurrentWeather.postValue(fetchedCurrentWeather)
        } catch (e: NoConnectivityException) {
            Log.i("Connectivity", "No internet connection")
        }
    }
}