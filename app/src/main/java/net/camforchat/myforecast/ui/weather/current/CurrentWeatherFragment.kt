package net.camforchat.myforecast.ui.weather.current

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.current_weather_fragment.*

import net.camforchat.myforecast.R
import net.camforchat.myforecast.ui.base.ScopedFragment
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class CurrentWeatherFragment : ScopedFragment(), KodeinAware {
    override val kodein by closestKodein()
    private val viewModelFactory: CurrentWeatherViewModelFactory by instance()

    private lateinit var viewModel: CurrentWeatherViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.current_weather_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory).get(CurrentWeatherViewModel::class.java)

        bindUI()
        /**
        val connectivityInterceptor = ConnectivityInterceptorImpl(requireContext())
        val apiService = OpenWeatherMapAPIService(connectivityInterceptor)

        GlobalScope.launch(Dispatchers.Main) {
            val currentWeatherResponse = apiService.getCurrentWeather("Ekaterinburg")

            textView.text = currentWeatherResponse.main.temp.toString()
        }
        **/
    }

    private fun bindUI() {
        viewModel.weather.observe(viewLifecycleOwner, Observer {
            textView.text = it.toString()
        })
    }

}
