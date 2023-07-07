package com.example.weatherapp2.di

import androidx.viewbinding.BuildConfig
import com.example.weatherapp2.BuildConfig.BASE_URL
import com.example.weatherapp2.data.service.WeatherService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object WeatherModule {

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient) =
        Retrofit.Builder()
            .baseUrl("BASE_URL")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(WeatherService::class.java)


    @Provides
    @Singleton
    fun provideClient(): OkHttpClient {
        val levelType: HttpLoggingInterceptor.Level = if (BuildConfig.DEBUG)
            HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE

        val logging = HttpLoggingInterceptor()
        logging.setLevel(levelType)

        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
    }
}