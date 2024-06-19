package dev.argraur.yandex.todo.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import dev.argraur.yandex.todo.navigation.TodoNavHost

@Composable
fun MainScreen(modifier: Modifier) {
    val navController = rememberNavController()
    TodoNavHost(navController, modifier)
}
