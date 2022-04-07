package com.itis.androidsemfour.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.itis.androidsemfour.di.ViewModelKey
import com.itis.androidsemfour.presentation.fragment.viewmodel.DetailsFragmentViewModel
import com.itis.androidsemfour.presentation.fragment.viewmodel.SearchListFragmentViewModel
import com.itis.androidsemfour.utils.AppViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @Binds
    fun bindViewModelFactory(
        factory: AppViewModelFactory
    ): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(SearchListFragmentViewModel::class)
    fun bindSearchFragmentViewModel(
        viewModel: SearchListFragmentViewModel
    ): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DetailsFragmentViewModel::class)
    fun bindDetailFragmentViewModel(
        viewModel: DetailsFragmentViewModel
    ): ViewModel
}
