package com.example.myapplication.data

import com.example.myapplication.data.model.Weather
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import javax.inject.Inject
import javax.inject.Singleton

const val BASE_URL_USER = "https://api.weatherapi.com/v1/"

@Singleton
class RetrofitServices @Inject constructor(
    private val client: OkHttpClient,
    private val retrofit: Retrofit
) {
    val searchWeatherApi: WeatherApi = retrofit.create(WeatherApi::class.java)
}

interface WeatherApi {
    @Headers(
        "Accept: application/json",
        "Content-type: application/json"
    )
    @GET("current.json")
    suspend fun getCurrentWeather(
        @Query("key") apiKey: String,
        @Query("q") city: String,
        @Query("aqi") aqi: String
    ): Weather
}