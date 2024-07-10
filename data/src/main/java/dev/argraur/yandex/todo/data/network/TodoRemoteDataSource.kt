package dev.argraur.yandex.todo.data.network

import dev.argraur.yandex.todo.data.metadata.MetadataStorage
import dev.argraur.yandex.todo.data.network.dto.TodoItemDTO
import dev.argraur.yandex.todo.data.network.dto.toNetworkTodoItem
import dev.argraur.yandex.todo.data.network.dto.toTodoItem
import dev.argraur.yandex.todo.domain.model.TodoItem
import javax.inject.Inject

class TodoRemoteDataSource @Inject constructor(
    private val todoService: TodoService,
    private val metadataStorage: MetadataStorage
) {
    suspend fun getTodoList(): List<TodoItem> {
        val items = todoService.getTodoItems()
        items.revision?.let { metadataStorage.setRevision(it) }
        return items.list.map { it.toTodoItem() }
    }

    suspend fun addTodoItem(item: TodoItem) {
        val response = todoService.addTodoItem(
            TodoItemDTO(element = item.toNetworkTodoItem()),
            metadataStorage.getRevision()
        )
        response.revision?.let { metadataStorage.setRevision(it) }
    }

    suspend fun deleteTodoItemById(id: String) {
        val response = todoService.deleteTodoItemById(id, metadataStorage.getRevision())
        response.revision?.let { metadataStorage.setRevision(it) }
    }

    suspend fun updateTodoItem(item: TodoItem) {
        val response = todoService.updateTodoItem(
            TodoItemDTO(element = item.toNetworkTodoItem()),
            revision = metadataStorage.getRevision()
        )
        response.revision?.let { metadataStorage.setRevision(it) }
    }
}