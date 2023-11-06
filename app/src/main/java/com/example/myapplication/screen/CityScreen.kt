package com.example.myapplication.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.myapplication.viewModel.CityScreenViewModel
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun CityScreen(navController: NavHostController) {

    val viewModel = hiltViewModel<CityScreenViewModel>()
    viewModel.getAllWeather()
    val weatherList by viewModel.weather.collectAsState()
    var openWeatherScreen by remember { mutableStateOf(false) }
    var idInDataBase by remember { mutableStateOf("0") }
    LaunchedEffect(openWeatherScreen){
        if(openWeatherScreen)navController.navigate(route = "weather_screen/$idInDataBase")
    }

    if (weatherList != null) {
        LazyColumn() {
            items(weatherList!!.size) { id ->
                val dateFormat = SimpleDateFormat("dd.MM.yyyy")
                val date = Date(weatherList!![id].date)
                val formattedDate = dateFormat.format(date)

                Card(
                    modifier = Modifier
                        .padding(
                            horizontal = 8.dp,
                            vertical = 4.dp
                        )
                        .clickable {
                            openWeatherScreen = true
                            idInDataBase = "${id + 1}"
                        }

                ) {
                    Row(
                        modifier = Modifier
                            .padding(
                                horizontal = 16.dp,
                                vertical = 8.dp
                            )
                            .fillMaxWidth()
                    ) {
                        Text(text = weatherList!![id].cityName)
                        Text(
                            text = formattedDate,
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )
                    }
                }
            }
        }
    }

}