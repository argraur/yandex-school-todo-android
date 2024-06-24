package dev.argraur.yandex.todo.ui.navigation

sealed class TodoRoute(val route: String) {
    data object Home : TodoRoute("home")
    data object Edit : TodoRoute("edit/{id}")
    data object New  : TodoRoute("new")
}