package com.example.myapplication.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.myapplication.R
import com.example.myapplication.data.State
import com.example.myapplication.viewModel.SearchScreenViewModel

@Composable
fun SearchScreen(navController: NavHostController) {
    val viewModel = hiltViewModel<SearchScreenViewModel>()
    val weather = viewModel.weather.collectAsState()
    val state = viewModel.state.collectAsState()
    var openWeatherScreen by remember { mutableStateOf(false) }

    LaunchedEffect(openWeatherScreen) {
        if (openWeatherScreen) navController.navigate(route = "weather_screen/" + weather.value?.id)
    }

    Scaffold(
        topBar = {
            SearchTopBar(
                city = {
                    viewModel.getWeather(it)
                },
                progress = state,
                lastSearch = weather.value?.cityName
            )
        },
    ) { paddingValue ->
        val modifier = Modifier.padding(top = paddingValue.calculateTopPadding())
        Column(modifier = modifier) {
            when (state.value) {
                State.Success -> CityNameCard(
                    cityName = weather.value?.cityName!!,
                    openWeather = { openWeatherScreen = true }

                )
                State.Error -> ErrorCard()
                else -> {}
            }
        }
    }
}

@Composable
fun ErrorCard() {
    Card(
        modifier = Modifier.padding(vertical = 10.dp)
    ) {
        Text(
            modifier = Modifier
                .padding(all = 10.dp)
                .fillMaxWidth(),
            text = stringResource(id = R.string.error)
        )
    }
}

@Composable
fun CityNameCard(
    cityName: String,
    openWeather: () -> Unit
) {
    Card(
        modifier = Modifier
            .padding(vertical = 10.dp)
            .clickable {
                openWeather()
            }
    ) {
        Text(
            modifier = Modifier
                .padding(all = 10.dp)
                .fillMaxWidth(),
            text = cityName
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun SearchTopBar(
    city: (String) -> Unit,
    progress: androidx.compose.runtime.State<State>,
    lastSearch: String?
) {

    var errorInput by remember { mutableStateOf(false) }
    val labelText = if (errorInput) {
        stringResource(id = R.string.english_letter)
    } else {
        stringResource(id = R.string.search)
    }
    var selectedText by remember { mutableStateOf("") }
    var textfieldSize by remember { mutableStateOf(Size.Zero) }
    var state by remember { mutableStateOf(false) }
    state = when (progress.value) {
        State.Loading -> false
        else -> true
    }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }
    var expanded by remember { mutableStateOf(false) }

    Column() {
        OutlinedTextField(
            isError = errorInput,
            value = selectedText,
            onValueChange = { input ->
                if (input.all { it.isLetter() && it in 'a'..'z' || it in 'A'..'Z' }) {
                    errorInput = false
                    selectedText = input
                } else {
                    errorInput = true
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned { coordinates ->
                    textfieldSize = coordinates.size.toSize()
                }
                .focusRequester(focusRequester)
                .onFocusChanged { focusState ->
                    if (focusState.isFocused) {
                        expanded = true
                    }
                },
            label = { Text(labelText) },
            singleLine = true,
            enabled = state,
            readOnly = false,
            trailingIcon = {
                when (progress.value) {
                    State.Loading -> CircularProgressIndicator()
                    else ->
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = stringResource(id = R.string.search),
                            modifier = Modifier.clickable {
                                city(selectedText)
                                expanded = false
                            }
                        )
                }
            },
        )
        if (lastSearch != null) {
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = {
                    expanded = false
                    keyboardController?.show()
                },
                modifier = Modifier
                    .width(with(LocalDensity.current) { textfieldSize.width.toDp() })
            ) {
                DropdownMenuItem(
                    onClick = {
                        selectedText = lastSearch
                        expanded = false
                        city(selectedText)
                    },
                    text = {
                        Text(text = lastSearch)

                    }
                )
            }
        }
    }
}