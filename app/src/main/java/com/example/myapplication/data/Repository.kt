package com.example.myapplication.data

import com.example.myapplication.data.room.WeatherDao
import com.example.myapplication.data.room.WeatherData
import javax.inject.Inject


class Repository @Inject constructor(
    private val weatherDao: WeatherDao,
    private val retrofit: RetrofitServices
) {
    suspend fun getWeather(city: String): WeatherData? {

        val weather = retrofit.searchWeatherApi.getCurrentWeather(
            apiKey = "bc97cd99101d48e289f82456231904",
            city = city,
            aqi = "no"
        )
        val searchCity = weather.location?.name!!
        var weatherData = weatherDao.getWeatherByCity(searchCity)

        val currentTime = System.currentTimeMillis()

        if (weatherData == null) {
            val insertData = WeatherData(cityName = searchCity, date = currentTime, weather = weather)
            weatherDao.insert(insertData)
            return weatherDao.getWeatherByCity(searchCity)
        } else {

            weatherData = weatherData.copy(date = currentTime, weather = weather)
            weatherDao.update(weatherData)
            return weatherDao.getWeatherByCity(searchCity)
        }
    }

    suspend fun getWeatherByCity(city: String): WeatherData? {
        return weatherDao.getWeatherByCity(city)
    }

    fun getWeatherByID(id: String): WeatherData? {
        return id.toIntOrNull()?.let { weatherDao.getWeatherById(it) }

    }

    fun getAllWeather(): List<WeatherData>{
        return weatherDao.getAll()
    }

}