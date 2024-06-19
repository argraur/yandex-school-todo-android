package dev.argraur.yandex.todo.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import dev.argraur.yandex.todo.ui.screens.HomeScreen

sealed class TodoRoute(val route: String) {
    data object Home : TodoRoute("home")
}

@Composable
fun TodoNavHost(navController: NavHostController, modifier: Modifier) {
    NavHost(
        navController = navController,
        startDestination = TodoRoute.Home.route,
        modifier = modifier
    ) {
        composable(TodoRoute.Home.route) { HomeScreen() }
    }
}