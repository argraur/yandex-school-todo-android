package dev.argraur.yandex.todo.domain.repository

import dev.argraur.yandex.todo.domain.model.TodoItem
import kotlinx.coroutines.flow.StateFlow

interface TodoItemsRepository {
    fun getTodoItems(): StateFlow<List<TodoItem>>
    fun getTodoById(id: String): TodoItem
    suspend fun addTodoItem(item: TodoItem): Boolean
    suspend fun updateTodoItem(item: TodoItem): Boolean
    suspend fun removeTodoItem(id: String): Boolean
}