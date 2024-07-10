package dev.argraur.yandex.todo.domain.repository

import dev.argraur.yandex.todo.domain.model.TodoItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface TodoItemsRepository {
    suspend fun refreshRepository()
    fun getTodoItems(): Flow<List<TodoItem>>
    suspend fun getTodoById(id: String): TodoItem
    suspend fun addTodoItem(item: TodoItem)
    suspend fun updateTodoItem(item: TodoItem)
    suspend fun removeTodoItem(id: String)
}