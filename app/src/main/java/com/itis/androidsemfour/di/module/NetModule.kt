package com.itis.androidsemfour.di.module

import com.itis.androidsemfour.BuildConfig
import com.itis.androidsemfour.data.api.Api
import com.itis.androidsemfour.di.qualifier.ApiInterceptorQualifier
import com.itis.androidsemfour.di.qualifier.LoggingInterceptorQualifier
import com.itis.androidsemfour.di.qualifier.UnitsInterceptorQualifier
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named

private const val BASE_URL = "https://api.openweathermap.org/data/2.5/"
private const val API_KEY = "3917f9e2c8cbd4b0359e5f15e9c21a89"
private const val QUERY_API_KEY = "appid"
private const val QUERY_UNITS = "units"
private const val UNITS = "metric"

@Module
@InstallIn(SingletonComponent::class)
class NetModule {
    @Provides
    @ApiInterceptorQualifier
    fun apiKeyInterceptor(): Interceptor = Interceptor { chain ->
        val original = chain.request()
        val newURL = original.url.newBuilder()
            .addQueryParameter(QUERY_API_KEY, API_KEY)
            .build()

        chain.proceed(
            original.newBuilder()
                .url(newURL)
                .build()
        )
    }

    @Provides
    @UnitsInterceptorQualifier
    fun unitsInterceptor(): Interceptor = Interceptor { chain ->
        val original = chain.request()
        val newURL: HttpUrl = original.url.newBuilder()
            .addQueryParameter(QUERY_UNITS, UNITS)
            .build()

        chain.proceed(
            original.newBuilder()
                .url(newURL)
                .build()
        )
    }

    @Provides
    @LoggingInterceptorQualifier
    fun provideLoggingInterceptor(): Interceptor {
        return HttpLoggingInterceptor()
            .setLevel(
                HttpLoggingInterceptor.Level.BODY
            )
    }

    @Provides
    fun okhttp(
        @ApiInterceptorQualifier apiKeyInterceptor: Interceptor,
        @LoggingInterceptorQualifier loggingInterceptor: Interceptor,
        @UnitsInterceptorQualifier unitsInterceptor: Interceptor
    ): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(apiKeyInterceptor)
            .addInterceptor(unitsInterceptor)
            .also {
                if (BuildConfig.DEBUG) {
                    it.addInterceptor(loggingInterceptor)
                }
            }
            .build()

    @Provides
    fun provideGsonConverter(): GsonConverterFactory = GsonConverterFactory.create()

    @Provides
    fun providesApi(
        okHttpClient: OkHttpClient,
        gsonConverter: GsonConverterFactory,
    ): Api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(gsonConverter)
        .build()
        .create(Api::class.java)

}
