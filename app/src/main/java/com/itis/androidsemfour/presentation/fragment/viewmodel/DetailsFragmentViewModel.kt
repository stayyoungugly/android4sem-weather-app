package com.itis.androidsemfour.presentation.fragment.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.itis.androidsemfour.domain.entity.WeatherEntity
import com.itis.androidsemfour.domain.usecase.GetWeatherByIdUseCase
import kotlinx.coroutines.launch

class DetailsFragmentViewModel(
    private val getWeatherByIdUseCase: GetWeatherByIdUseCase
) : ViewModel() {
    private var _weather: MutableLiveData<Result<WeatherEntity>> = MutableLiveData()
    val weather: LiveData<Result<WeatherEntity>> = _weather

    private var _error: MutableLiveData<Exception> = MutableLiveData()
    val error: LiveData<Exception> = _error

    fun getWeatherById(id: Int) {
        viewModelScope.launch {
            try {
                val weather = getWeatherByIdUseCase(id)
                _weather.value = Result.success(weather)
            } catch (ex: Exception) {
                _weather.value = Result.failure(ex)
                _error.value = ex
            }
        }
    }
}
