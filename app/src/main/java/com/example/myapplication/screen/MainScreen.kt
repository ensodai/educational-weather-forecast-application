package com.example.myapplication.screen

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.navigation.BottomNavigation
import com.example.myapplication.navigation.NavHost

import com.example.myapplication.ui.theme.MyApplicationTheme

@Composable
fun MainScreen() {
    var paddingValues = ""
    val navController = rememberNavController()
    MyApplicationTheme() {
        Scaffold(
            bottomBar = { BottomNavigation(navController = navController) }
        ) {paddingValue ->
            paddingValues = paddingValue.toString()
            NavHost(navController = navController)

        }
    }
}