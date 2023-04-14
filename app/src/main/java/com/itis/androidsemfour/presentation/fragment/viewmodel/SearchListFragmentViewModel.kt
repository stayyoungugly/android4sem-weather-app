package com.itis.androidsemfour.presentation.fragment.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.itis.androidsemfour.domain.entity.CityEntity
import com.itis.androidsemfour.domain.entity.WeatherEntity
import com.itis.androidsemfour.domain.usecase.GetCitiesUseCase
import com.itis.androidsemfour.domain.usecase.GetWeatherByNameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchListFragmentViewModel @Inject constructor(
    private val getCitiesUseCase: GetCitiesUseCase,
    private val getWeatherByNameUseCase: GetWeatherByNameUseCase
) : ViewModel() {

    private val _cityList: MutableLiveData<Result<List<CityEntity>>> = MutableLiveData()
    val cityList: LiveData<Result<List<CityEntity>>> = _cityList

    private var _weather: MutableLiveData<Result<WeatherEntity>> = MutableLiveData()
    val weather: LiveData<Result<WeatherEntity>> = _weather

    private var _error: MutableLiveData<Exception> = MutableLiveData()
    val error: LiveData<Exception> = _error

    fun getCityList(cityLatitude: Double, cityLongitude: Double, count: Int) {
        viewModelScope.launch {
            try {
                val cityList = getCitiesUseCase(cityLatitude, cityLongitude, count)
                _cityList.value = Result.success(cityList)
            } catch (ex: Exception) {
                _cityList.value = Result.failure(ex)
                _error.value = ex
            }
        }
    }

    fun getWeatherByName(name: String) {
        viewModelScope.launch {
            try {
                val weather = getWeatherByNameUseCase(name)
                _weather.value = Result.success(weather)
            } catch (ex: Exception) {
                _weather.value = Result.failure(ex)
                _error.value = ex
            }
        }
    }
}
