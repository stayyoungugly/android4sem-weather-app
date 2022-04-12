package com.itis.androidsemfour.presentation.fragment.viewmodel

import androidx.lifecycle.*
import com.itis.androidsemfour.domain.entity.WeatherEntity
import com.itis.androidsemfour.domain.usecase.GetWeatherByIdUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch


@HiltViewModel
class DetailsFragmentViewModel @AssistedInject constructor(
    private val getWeatherByIdUseCase: GetWeatherByIdUseCase,
    @Assisted private val cityId: Int
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

    @AssistedFactory
    interface WeatherViewModelFactory {
        fun create(cityId: Int): DetailsFragmentViewModel
    }

    @Suppress("UNCHECKED_CAST")
    companion object {
        fun provideFactory(
            assistedFactory: WeatherViewModelFactory,
            cityId: Int
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T =
                assistedFactory.create(cityId) as T
        }
    }
}
