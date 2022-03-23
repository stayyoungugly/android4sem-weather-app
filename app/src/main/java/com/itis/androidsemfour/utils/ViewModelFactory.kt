package com.itis.androidsemfour.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.itis.androidsemfour.di.DIContainer
import com.itis.androidsemfour.presentation.fragment.viewmodel.DetailsFragmentViewModel
import com.itis.androidsemfour.presentation.fragment.viewmodel.SearchListFragmentViewModel

class ViewModelFactory(
    private val di: DIContainer,
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        when {
            modelClass.isAssignableFrom(SearchListFragmentViewModel::class.java) ->
                SearchListFragmentViewModel(di.getCitiesUseCase, di.getWeatherByNameUseCase)
                        as? T ?: throw IllegalArgumentException("Unknown ViewModel class")
            modelClass.isAssignableFrom(DetailsFragmentViewModel::class.java) ->
                DetailsFragmentViewModel(di.getWeatherByIdUseCase)
                        as? T ?: throw IllegalArgumentException("Unknown ViewModel class")
            else ->
                throw IllegalArgumentException("Unknown ViewModel class")
        }
}
