package net.camforchat.myforecast.ui.weather.current

import androidx.lifecycle.*
import net.camforchat.myforecast.data.db.entity.CurrentWeatherEntry
import net.camforchat.myforecast.data.repository.ForecastRepository

class CurrentWeatherViewModel(
    private val forecastRepository: ForecastRepository
) : ViewModel() {
    val isMetric = MutableLiveData<Boolean>(true)

    val weather: LiveData<CurrentWeatherEntry> = isMetric.switchMap {
        liveData(context = viewModelScope.coroutineContext) {
            emitSource(forecastRepository.getCurrentWeather(it))
        }
    }
}
