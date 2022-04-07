package com.itis.androidsemfour.di

import com.itis.androidsemfour.di.module.AppModule
import com.itis.androidsemfour.di.module.NetModule
import com.itis.androidsemfour.di.module.RepositoryModule
import com.itis.androidsemfour.di.module.ViewModelModule
import com.itis.androidsemfour.presentation.activity.MainActivity
import com.itis.androidsemfour.presentation.fragment.DetailsFragment
import com.itis.androidsemfour.presentation.fragment.SearchListFragment
import dagger.Component

@Component(
    modules = [
        AppModule::class,
        NetModule::class,
        RepositoryModule::class,
        ViewModelModule::class
    ]
)
interface AppComponent {
    fun inject(mainActivity: MainActivity)
    fun inject(searchListFragment: SearchListFragment)
    fun inject(detailsFragment: DetailsFragment)
}
