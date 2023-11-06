package com.example.myapplication.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [WeatherData::class], version = 1)
@TypeConverters(WeatherTypeConverter::class)
abstract class WeatherDatabase : RoomDatabase() {

    abstract fun weatherDao(): WeatherDao

    companion object {
        @Volatile
        private var INSTANCE: WeatherDatabase? = null

        fun getInstance(context: Context): WeatherDatabase {
             return INSTANCE ?: Room.databaseBuilder(
                 context.applicationContext,
                 WeatherDatabase::class.java,
                 "weather_database"
             ).build().also { INSTANCE = it }
        }
    }
}