package com.example.myapplication.viewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.Repository
import com.example.myapplication.data.room.WeatherData
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class WeatherScreenViewModel@Inject constructor(
    @ApplicationContext private val context: Context,
    private val repository: Repository
): ViewModel(){

    private val _weather = MutableStateFlow<WeatherData?>(null)
    val weather = _weather.asStateFlow()

    fun getWeatherCityById(id: String){
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _weather.value = repository.getWeatherByID(id)
            }
        }
    }

}