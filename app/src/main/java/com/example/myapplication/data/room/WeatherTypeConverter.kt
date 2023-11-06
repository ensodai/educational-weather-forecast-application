package com.example.myapplication.data.room

import androidx.room.TypeConverter
import com.example.myapplication.data.model.Weather
import com.squareup.moshi.Moshi

class WeatherTypeConverter {
    private val moshi = Moshi.Builder().build()

    @TypeConverter
    fun fromWeather(weather: Weather): String {
        return moshi.adapter(Weather::class.java).toJson(weather)
    }

    @TypeConverter
    fun toWeather(json: String): Weather {
        return moshi.adapter(Weather::class.java).fromJson(json)!!
    }
}

