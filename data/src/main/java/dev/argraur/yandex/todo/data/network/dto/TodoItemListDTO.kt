package dev.argraur.yandex.todo.data.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class TodoItemListDTO (
    val status: String,
    val list: List<NetworkTodoItem>,
    val revision: Int? = null
)