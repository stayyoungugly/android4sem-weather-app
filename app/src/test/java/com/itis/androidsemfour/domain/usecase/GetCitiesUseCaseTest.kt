package com.itis.androidsemfour.domain.usecase

import com.itis.androidsemfour.data.response.Weather
import com.itis.androidsemfour.domain.entity.CityEntity
import com.itis.androidsemfour.domain.entity.WeatherEntity
import com.itis.androidsemfour.domain.repository.WeatherRepository
import com.itis.androidsemfour.utils.MainCoroutineRule
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule

import org.junit.Test
import org.junit.jupiter.api.DisplayName
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import kotlin.math.exp

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(JUnit4::class)
class GetCitiesUseCaseTest {
    @MockK
    lateinit var repository: WeatherRepository

    @get:Rule
    val coroutineRule: MainCoroutineRule = MainCoroutineRule()

    private lateinit var useCase: GetCitiesUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        useCase = spyk(GetCitiesUseCase(repository, coroutineRule.testDispatcher))
    }

    @Test
    @DisplayName("Successful getting cities")
    operator fun invoke() = runBlocking {
        val expectedList = arrayListOf<CityEntity>(
            mockk { every { id } returns 1 },
            mockk { every { id } returns 2 },
        )

        coEvery { repository.getCities(any(), any(), any()) } returns expectedList

        val result = useCase.invoke(1.0, 1.0, 5)

        assertEquals(expectedList, result)
    }
}

