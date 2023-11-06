package com.example.myapplication.viewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.Repository
import com.example.myapplication.data.State
import com.example.myapplication.data.room.WeatherData
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchScreenViewModel@Inject constructor(
    @ApplicationContext private val context: Context,
    private val repository: Repository
): ViewModel() {
    private val _weather = MutableStateFlow<WeatherData?>(null)
    val weather = _weather.asStateFlow()

    private val _state = MutableStateFlow<State>(State.Start)
    val state = _state.asStateFlow()

    fun getWeather(city: String){
        viewModelScope.launch {
            _state.value = State.Loading
            try {
                _weather.value = repository.getWeather(city)
                _state.value = State.Success
            } catch (e: Throwable){
                val cityInDatabase = repository.getWeatherByCity(city)
                if (city == cityInDatabase?.cityName){
                    _weather.value = cityInDatabase
                    _state.value = State.Success
                } else _state.value = State.Error
            }
        }
    }

}