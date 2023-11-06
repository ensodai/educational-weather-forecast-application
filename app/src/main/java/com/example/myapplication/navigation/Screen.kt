package com.example.myapplication.navigation

sealed class Screen(val route: String) {
    object SearchScreen: Screen("search_screen")
    object CityScreen: Screen("city_screen")
    object WeatherScreen: Screen("weather_screen/{id}")
}