package dev.argraur.yandex.todo.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.argraur.yandex.todo.data.database.entity.TodoItemEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoItemDao {
    @Query("SELECT * FROM TodoItemEntity")
    fun getAll(): Flow<List<TodoItemEntity>>

    @Query("SELECT * FROM TodoItemEntity WHERE id = :id LIMIT 1")
    fun getById(id: String): TodoItemEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: TodoItemEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg items: TodoItemEntity)

    @Query("DELETE FROM TodoItemEntity WHERE id = :id")
    fun deleteById(id: String)
}