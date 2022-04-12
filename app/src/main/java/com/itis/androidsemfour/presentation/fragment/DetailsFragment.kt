package com.itis.androidsemfour.presentation.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.itis.androidsemfour.R
import com.itis.androidsemfour.databinding.FragmentDetailsBinding
import com.itis.androidsemfour.domain.entity.WeatherEntity
import com.itis.androidsemfour.presentation.activity.MainActivity
import com.itis.androidsemfour.presentation.fragment.viewmodel.DetailsFragmentViewModel
import com.itis.androidsemfour.utils.ViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class DetailsFragment : Fragment(R.layout.fragment_details) {
    private lateinit var binding: FragmentDetailsBinding

    private val glide by lazy {
        Glide.with(this)
    }

    private val id by lazy {
        arguments?.getInt("id")
    }

    private val viewModel: DetailsFragmentViewModel by viewModels { factory }


    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDetailsBinding.bind(view)
        initObservers()
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
        binding.weather = weatherEntity
        val weatherIcon = weatherEntity.icon
        val options = RequestOptions()
            .priority(Priority.HIGH)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
        with(binding) {
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
}
