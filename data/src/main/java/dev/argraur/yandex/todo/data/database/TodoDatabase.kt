package dev.argraur.yandex.todo.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import dev.argraur.yandex.todo.data.database.dao.TodoItemDao
import dev.argraur.yandex.todo.data.database.entity.TodoItemEntity

@Database(
    entities = [TodoItemEntity::class],
    version = 1
)
abstract class TodoDatabase: RoomDatabase() {
    abstract fun todoItemDao(): TodoItemDao
}