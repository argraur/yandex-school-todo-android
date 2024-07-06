package dev.argraur.yandex.todo.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import dev.argraur.yandex.todo.core.extensions.fromEpochMillis
import dev.argraur.yandex.todo.core.extensions.toEpochMillis
import dev.argraur.yandex.todo.domain.model.TodoItem
import dev.argraur.yandex.todo.domain.model.Urgency
import kotlinx.datetime.LocalDate

@Entity
data class TodoItemEntity(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "text") val text: String,
    @ColumnInfo(name = "urgency") val urgency: String,
    @ColumnInfo(name = "done") val done: Boolean,
    @ColumnInfo(name = "creation_date") val creationDate: Long,
    @ColumnInfo(name = "update_date") val updateDate: Long? = null,
    @ColumnInfo(name = "deadline_date") val deadline: Long? = null
)

fun TodoItemEntity.toTodoItem(): TodoItem =
    TodoItem(
        id = id,
        text = text,
        urgency = when (urgency) {
            "low" -> Urgency.Low
            "urgent" -> Urgency.Urgent
            "normal" -> Urgency.Normal
            else -> throw IllegalArgumentException("Unknown urgency type!")
        },
        done = done,
        creationDate = LocalDate.fromEpochMillis(creationDate),
        updateDate = if (updateDate != null) LocalDate.fromEpochMillis(updateDate) else null,
        deadline = if (deadline != null) LocalDate.fromEpochMillis(deadline) else null,
    )

fun TodoItem.toTodoItemEntity(): TodoItemEntity =
    TodoItemEntity(
        id = id,
        text = text,
        urgency = when(urgency) {
            Urgency.Low -> "low"
            Urgency.Urgent -> "urgent"
            Urgency.Normal -> "normal"
        },
        done = done,
        creationDate = creationDate.toEpochMillis(),
        updateDate = updateDate?.toEpochMillis(),
        deadline = deadline?.toEpochMillis()
    )