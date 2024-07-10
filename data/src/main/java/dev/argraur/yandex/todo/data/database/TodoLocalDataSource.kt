package dev.argraur.yandex.todo.data.database

import dev.argraur.yandex.todo.data.database.entity.TodoItemEntity
import dev.argraur.yandex.todo.data.database.entity.toTodoItem
import dev.argraur.yandex.todo.data.database.entity.toTodoItemEntity
import dev.argraur.yandex.todo.domain.model.TodoItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TodoLocalDataSource @Inject constructor(db: TodoDatabase) {
    private val dao = db.todoItemDao()

    fun getTodoItems(): Flow<List<TodoItem>> = dao.getAll().map { it.map(TodoItemEntity::toTodoItem) }

    fun getTodoItemById(id: String): TodoItem = dao.getById(id).toTodoItem()

    fun insertTodoItems(list: List<TodoItem>) = dao.insertAll(*(list.map(TodoItem::toTodoItemEntity).toTypedArray()))

    fun insertTodoItem(item: TodoItem) = dao.insert(item.toTodoItemEntity())

    fun deleteTodoItemById(id: String) = dao.deleteById(id)
}