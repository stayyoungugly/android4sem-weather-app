package com.itis.androidsemfour

import android.app.Application
import com.itis.androidsemfour.di.AppComponent
import com.itis.androidsemfour.di.DaggerAppComponent
import com.itis.androidsemfour.di.module.AppModule
import com.itis.androidsemfour.di.module.NetModule

class App : Application() {
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent
            .builder()
            .appModule(AppModule())
            .netModule(NetModule())
            .build()
    }
}
