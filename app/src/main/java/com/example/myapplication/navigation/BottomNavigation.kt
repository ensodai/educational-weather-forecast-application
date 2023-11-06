package com.example.myapplication.navigation

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination

@Composable
fun BottomNavigation(navController: NavController) {

    val listItem = listOf(
        ButtonItem.SearchButton,
        ButtonItem.CityButton
    )

    var selectedItem by remember { mutableStateOf(0) }

    NavigationBar(modifier = Modifier.height(64.dp)) {
        listItem.forEachIndexed { index, item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = item.iconId,
                        contentDescription = item.title
                    )
                },
                label = {
                    Text(
                        text = item.title,
                        modifier = Modifier.offset(y = 10.dp)
                    )
                },
                selected = selectedItem == index,
                onClick = {
                    selectedItem = index
                    navController.navigate(item.route) {
                        launchSingleTop = true
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        restoreState = true
                    }
                },
                alwaysShowLabel = false
            )
        }
    }
}