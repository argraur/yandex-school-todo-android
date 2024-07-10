package dev.argraur.yandex.todo.data.repository

import dev.argraur.yandex.todo.data.database.TodoLocalDataSource
import dev.argraur.yandex.todo.data.network.TodoRemoteDataSource
import dev.argraur.yandex.todo.domain.model.TodoItem
import dev.argraur.yandex.todo.domain.repository.TodoItemsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TodoItemsRepositoryImpl @Inject constructor(
    private val localDataSource: TodoLocalDataSource,
    private val remoteDataSource: TodoRemoteDataSource
) : TodoItemsRepository {
    private val ioContext = Dispatchers.IO + Job()

    override suspend fun refreshRepository() = withContext(ioContext) {
        val list = remoteDataSource.getTodoList()
        localDataSource.insertTodoItems(list)
    }

    override fun getTodoItems(): Flow<List<TodoItem>> =
        localDataSource.getTodoItems()

    override suspend fun getTodoById(id: String): TodoItem = withContext(ioContext) {
        localDataSource.getTodoItemById(id)
    }

    override suspend fun addTodoItem(item: TodoItem) = withContext(ioContext) {
        localDataSource.insertTodoItem(item)
        remoteDataSource.addTodoItem(item)
    }

    override suspend fun updateTodoItem(item: TodoItem) = withContext(ioContext) {
        localDataSource.insertTodoItem(item)
        remoteDataSource.updateTodoItem(item)
    }

    override suspend fun removeTodoItem(id: String) = withContext(ioContext) {
        localDataSource.deleteTodoItemById(id)
        remoteDataSource.deleteTodoItemById(id)
    }
}