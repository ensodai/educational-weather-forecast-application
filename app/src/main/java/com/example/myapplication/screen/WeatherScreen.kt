package com.example.myapplication.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.myapplication.R
import com.example.myapplication.viewModel.WeatherScreenViewModel

@Composable
fun WeatherScreen(city: String) {

    val viewModel = hiltViewModel<WeatherScreenViewModel>()

    viewModel.getWeatherCityById(city)

    val weather by viewModel.weather.collectAsState()
    
    Card() {
        Column(modifier = Modifier.padding(all = 8.dp)) {
            Text(
                text = weather?.weather?.location?.region.toString(),
                modifier = Modifier.padding(vertical = 8.dp)
            )
            Row(
                modifier = Modifier.padding(vertical = 8.dp)
            ) {
                Text(text = stringResource(id = R.string.temperature))
                Text(
                    text = weather?.weather?.current?.tempC.toString(),

                )   
            }
            
        } 
    }
    
}