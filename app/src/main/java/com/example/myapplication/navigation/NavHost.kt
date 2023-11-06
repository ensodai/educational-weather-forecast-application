package com.example.myapplication.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.myapplication.screen.CityScreen
import com.example.myapplication.screen.SearchScreen
import com.example.myapplication.screen.WeatherScreen
import androidx.navigation.compose.NavHost


@Composable
fun NavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.SearchScreen.route
    ) {
        composable(route = Screen.SearchScreen.route) {
            SearchScreen(navController)
        }
        composable(
            route = Screen.CityScreen.route
        ) {
            CityScreen(navController)
        }
        composable(
            route = Screen.WeatherScreen.route,
            arguments = listOf(
                navArgument("id") {
                    type = NavType.StringType
                }
            )
        ) {
            WeatherScreen(city = it.arguments?.getString("id")!!)
        }
    }

}
