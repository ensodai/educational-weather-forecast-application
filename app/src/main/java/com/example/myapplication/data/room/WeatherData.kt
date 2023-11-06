package com.example.myapplication.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.myapplication.data.model.Weather

@Entity(tableName = "weather_data")
data class WeatherData(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val cityName: String,
    var date: Long,
    var weather: Weather?
)