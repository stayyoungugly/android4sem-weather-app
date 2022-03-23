package com.itis.androidsemfour.presentation.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.itis.androidsemfour.R
import com.itis.androidsemfour.databinding.FragmentDetailsBinding
import com.itis.androidsemfour.di.DIContainer
import com.itis.androidsemfour.domain.entity.WeatherEntity
import com.itis.androidsemfour.presentation.fragment.viewmodel.DetailsFragmentViewModel
import com.itis.androidsemfour.utils.ViewModelFactory
import kotlinx.coroutines.launch
import timber.log.Timber

class DetailsFragment : Fragment(R.layout.fragment_details) {
    private lateinit var binding: FragmentDetailsBinding
    private lateinit var glide: RequestManager
    private lateinit var viewModel: DetailsFragmentViewModel


    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDetailsBinding.bind(view)
        initObjects()
        initObservers()
        val id = arguments?.getInt("id")
        if (id != null) {
            getCityWeatherData(id)
        }
    }

    private fun getCityWeatherData(id: Int) {
        lifecycleScope.launch {
            viewModel.getWeatherById(id)
        }
    }

    private fun setCityWeatherData(weatherEntity: WeatherEntity) {
        val temp = weatherEntity.temp.toString() + "°С"
        val tempFeels = "Feels like " + weatherEntity.feelsLike.toString() + "°С"
        val cityName = weatherEntity.name
        val pressure = weatherEntity.pressure.toString() + " PA"
        val humidity = weatherEntity.humidity.toString() + "%"
        val wind = weatherEntity.windSpeed.toString() + " m/s"
        val weatherName = weatherEntity.description
        val weatherIcon = weatherEntity.icon
        val windDirection = weatherEntity.windDeg
        val options = RequestOptions()
            .priority(Priority.HIGH)
            .diskCacheStrategy(DiskCacheStrategy.ALL)

        with(binding) {
            tvTempNumber.text = temp
            tvTempFeels.text = tempFeels
            tvCity.text = cityName
            tvPressureNumber.text = pressure
            tvHumidityNumber.text = humidity
            tvWindNumber.text = wind
            tvWeatherName.text = weatherName
            tvDirectionPeak.text = when (windDirection) {
                in 0..22 -> "North"
                in 23..67 -> "North-East"
                in 68..112 -> "East"
                in 113..157 -> "South-East"
                in 158..202 -> "South"
                in 203..247 -> "South-West"
                in 248..292 -> "West"
                in 293..337 -> "North-West"
                in 337..361 -> "North"
                else -> "Not found"
            }
            glide.load(generateIcon(weatherIcon))
                .apply(options)
                .into(ivIcon)
        }
    }

    private fun generateIcon(iconId: String): String {
        val url = "http://openweathermap.org/img/wn/$iconId@2x.png"
        print(url)
        return url
    }

    private fun initObservers() {
        viewModel.weather.observe(viewLifecycleOwner) { weatherInfo ->
            weatherInfo.fold(onSuccess = {
                setCityWeatherData(it)
            }, onFailure = {
                Timber.e("City Info Not Found")
            })
        }
        viewModel.error.observe(viewLifecycleOwner) {
            Timber.e(it.message.toString())
        }
    }

    private fun initObjects() {
        glide = Glide.with(this)
        val factory = ViewModelFactory(DIContainer)
        viewModel = ViewModelProvider(
            this,
            factory
        )[DetailsFragmentViewModel::class.java]

    }
}
