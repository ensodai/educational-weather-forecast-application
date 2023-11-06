package com.example.myapplication.data.room
import androidx.room.*
import androidx.room.Dao

@Dao
interface WeatherDao {
    @Insert
    suspend fun insert(weatherData: WeatherData)

    @Update
    suspend fun update(weatherData: WeatherData)

    @Query("SELECT * FROM weather_data WHERE cityName = :cityName")
    suspend fun getWeatherData(cityName: String): WeatherData?

    @Query("SELECT * FROM weather_data WHERE cityName = :cityName LIMIT 1")
    suspend fun getWeatherByCity(cityName: String): WeatherData?

    @Query("SELECT * FROM weather_data WHERE id = :id")
    fun getWeatherById(id: Int): WeatherData?

    @Query("SELECT * FROM weather_data")
    fun getAll(): List<WeatherData>
}