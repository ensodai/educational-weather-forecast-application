package com.example.myapplication.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector

sealed class ButtonItem(
    val title: String,
    val iconId: ImageVector,
    val route: String
) {
    object SearchButton : ButtonItem(
        title = "Search",
        iconId = Icons.Filled.Search,
        route = Screen.SearchScreen.route
    )

    object CityButton : ButtonItem(
        title = "City",
        iconId = Icons.Filled.List,
        route = Screen.CityScreen.route
    )
}