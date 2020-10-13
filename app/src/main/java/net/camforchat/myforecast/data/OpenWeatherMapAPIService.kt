package net.camforchat.myforecast.data

import net.camforchat.myforecast.data.network.ConnectivityInterceptor
import net.camforchat.myforecast.data.network.ConnectivityInterceptorImpl
import net.camforchat.myforecast.data.network.response.CurrentWeatherResponse
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

const val API_KEY = "ded158f523a34b6713a87c4f3ef0d509"
const val API_BASE_URL = "https://api.openweathermap.org/data/2.5/"

interface OpenWeatherMapAPIService {
    // Example: https://api.openweathermap.org/data/2.5/weather?q=Ekaterinburg&appid=ded158f523a34b6713a87c4f3ef0d509&units=metric
    @GET("weather")
    suspend fun getCurrentWeather(
        @Query("q") location: String,
        @Query("units") units: String = "metric"
    ): CurrentWeatherResponse

    companion object {
        operator fun invoke(
            connectivityInterceptor: ConnectivityInterceptor
        ): OpenWeatherMapAPIService {
            val apiKeyInterceptor = Interceptor { chain ->
                val url = chain.request().url()
                    .newBuilder().addQueryParameter("appid", API_KEY).build()
                val request = chain.request().newBuilder().url(url).build()

                return@Interceptor chain.proceed(request)
            }

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(apiKeyInterceptor)
                .addInterceptor(connectivityInterceptor)
                .build()

            return Retrofit.Builder().client(okHttpClient)
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(OpenWeatherMapAPIService::class.java)
        }
    }
}