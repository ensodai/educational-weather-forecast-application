package com.example.myapplication.di

import android.content.Context
import com.example.myapplication.data.BASE_URL_USER
import com.example.myapplication.data.Repository
import com.example.myapplication.data.RetrofitServices
import com.example.myapplication.data.room.WeatherDao
import com.example.myapplication.data.room.WeatherDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL_USER)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideWeatherDao(@ApplicationContext context: Context): WeatherDao {
        return WeatherDatabase.getInstance(context).weatherDao()
    }

    @Provides
    @Singleton
    fun provideRepository(weatherDao: WeatherDao, retrofit: Retrofit): Repository {
        return Repository(weatherDao, RetrofitServices(provideOkHttpClient(), retrofit))
    }
}
