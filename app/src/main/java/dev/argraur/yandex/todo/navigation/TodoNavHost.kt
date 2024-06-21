package dev.argraur.yandex.todo.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import dev.argraur.yandex.todo.ui.navigation.TodoRoute
import dev.argraur.yandex.todo.ui.screens.EditTodoScreen
import dev.argraur.yandex.todo.ui.screens.HomeScreen

@Composable
fun TodoNavHost(navController: NavHostController, modifier: Modifier) {
    NavHost(
        navController = navController,
        startDestination = TodoRoute.Home.route,
        modifier = modifier
    ) {
        composable(TodoRoute.Home.route) { HomeScreen(navController, hiltViewModel()) }
        composable(TodoRoute.Edit.route) { EditTodoScreen(navController, hiltViewModel()) }
        composable(TodoRoute.New.route) { EditTodoScreen(navController, hiltViewModel()) }
    }
}