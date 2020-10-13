package net.camforchat.myforecast

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import net.camforchat.myforecast.data.OpenWeatherMapAPIService
import net.camforchat.myforecast.data.db.ForecastDatabase
import net.camforchat.myforecast.data.network.ConnectivityInterceptor
import net.camforchat.myforecast.data.network.ConnectivityInterceptorImpl
import net.camforchat.myforecast.data.network.WeatherNetworkDataSource
import net.camforchat.myforecast.data.network.WeatherNetworkDataSourceImpl
import net.camforchat.myforecast.data.repository.ForecastRepository
import net.camforchat.myforecast.data.repository.ForecastRepositoryImpl
import net.camforchat.myforecast.ui.weather.current.CurrentWeatherViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class App: Application(), KodeinAware {
    override val kodein by Kodein.lazy {
        import(androidXModule(this@App))

        bind() from singleton { ForecastDatabase(instance()) }
        bind() from singleton { instance<ForecastDatabase>().currentWeatherDao() }
        bind<ConnectivityInterceptor>() with singleton { ConnectivityInterceptorImpl(instance()) }
        bind() from singleton { OpenWeatherMapAPIService(instance()) }
        bind<WeatherNetworkDataSource>() with singleton { WeatherNetworkDataSourceImpl(instance()) }
        bind<ForecastRepository>() with singleton { ForecastRepositoryImpl(instance(), instance()) }
        bind() from provider { CurrentWeatherViewModelFactory(instance()) }
    }

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
    }
}