package dev.argraur.yandex.todo.domain.repository

import dev.argraur.yandex.todo.domain.model.TodoItem
import kotlinx.coroutines.flow.StateFlow

interface TodoItemsRepository {
    fun getTodoItems(): StateFlow<List<TodoItem>>
    fun getTodoById(id: String): TodoItem
    fun addTodoItem(item: TodoItem): Boolean
    fun updateTodoItem(item: TodoItem): Boolean
    fun removeTodoItem(id: String): Boolean
}