package dev.argraur.yandex.todo.data.repository

import dev.argraur.yandex.todo.data.source.MockTodoItemsDataSource
import dev.argraur.yandex.todo.domain.model.TodoItem
import dev.argraur.yandex.todo.domain.repository.TodoItemsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TodoItemsRepositoryImpl @Inject constructor(
    private val dataSource: MockTodoItemsDataSource
) : TodoItemsRepository {
    private val _todoItemsFlow = MutableStateFlow<List<TodoItem>>(emptyList())
    private val coroutineContext = Dispatchers.Default

    init {
        _todoItemsFlow.value = dataSource.getTodoItems()
    }

    override fun getTodoItems(): StateFlow<List<TodoItem>> =
        _todoItemsFlow.asStateFlow()

    override fun getTodoById(id: String): TodoItem =
        _todoItemsFlow.value.first { it.id == id }

    override suspend fun addTodoItem(item: TodoItem): Boolean = withContext(coroutineContext) {
        _todoItemsFlow.value += item
        return@withContext dataSource.addTodoItem(item)
    }

    override suspend fun updateTodoItem(item: TodoItem): Boolean = withContext(coroutineContext) {
        _todoItemsFlow.update { items ->
            items.map { if (it.id == item.id) item else it }
        }
        return@withContext dataSource.updateTodoItem(item)
    }

    override suspend fun removeTodoItem(id: String): Boolean = withContext(coroutineContext) {
        _todoItemsFlow.update { items ->
            items.filterNot { it.id == id }
        }
        return@withContext dataSource.removeTodoItem(id)
    }
}