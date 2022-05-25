package com.itis.androidsemfour.presentation.fragment.viewmodel

import com.itis.androidsemfour.data.response.Weather
import com.itis.androidsemfour.domain.entity.WeatherEntity
import com.itis.androidsemfour.domain.usecase.GetWeatherByIdUseCase
import com.itis.androidsemfour.utils.getOrAwaitValue
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.DisplayName

class DetailsFragmentViewModelTest {

    @MockK
    lateinit var getWeatherByIdUseCase: GetWeatherByIdUseCase

    private lateinit var viewModel: DetailsFragmentViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = DetailsFragmentViewModel(getWeatherByIdUseCase, 1)
    }

    @Test
    @DisplayName("When fun is called, livedata is set")
    fun getWeatherById() = runBlocking {

        val mockWeather = mockk<WeatherEntity> { every { id } returns 1 }
        val expectedWeather = Result.success(mockWeather)

        coEvery { getWeatherByIdUseCase.invoke(any()) } returns mockWeather

        viewModel.getWeatherById()

        assertEquals(expectedWeather, viewModel.weather.getOrAwaitValue())
    }
}
