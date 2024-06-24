package dev.argraur.yandex.todo.data.repository

import dev.argraur.yandex.todo.data.source.MockTodoItemsDataSource
import dev.argraur.yandex.todo.domain.model.TodoItem
import dev.argraur.yandex.todo.domain.repository.TodoItemsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class TodoItemsRepositoryImpl @Inject constructor(
    private val dataSource: MockTodoItemsDataSource
) : TodoItemsRepository {
    private val _todoItemsFlow = MutableStateFlow<List<TodoItem>>(emptyList())

    init {
        _todoItemsFlow.value = dataSource.getTodoItems()
    }

    override fun getTodoItems(): StateFlow<List<TodoItem>> {
        return _todoItemsFlow.asStateFlow()
    }

    override fun getTodoById(id: String): TodoItem {
        return _todoItemsFlow.value.first { it.id == id }
    }

    override fun addTodoItem(item: TodoItem): Boolean {
        _todoItemsFlow.value += item
        return dataSource.addTodoItem(item)
    }

    override fun updateTodoItem(item: TodoItem): Boolean {
        _todoItemsFlow.update { items ->
            items.map { if (it.id == item.id) item else it }
        }
        return dataSource.updateTodoItem(item)
    }

    override fun removeTodoItem(id: String): Boolean {
        _todoItemsFlow.update { items ->
            items.filterNot { it.id == id }
        }
        return dataSource.removeTodoItem(id)
    }
}