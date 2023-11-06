package com.example.myapplication.data

sealed class State {

    object Start: State()

    object Loading : State()

    object Success: State()

    object Error: State()

}