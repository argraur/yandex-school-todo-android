package dev.argraur.yandex.todo.data.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class TodoItemDTO (
    val status: String = "",
    val element: NetworkTodoItem,
    val revision: Int? = null
)