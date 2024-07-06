package dev.argraur.yandex.todo.data.network.dto

import dev.argraur.yandex.todo.core.extensions.fromEpochMillis
import dev.argraur.yandex.todo.core.extensions.toEpochMillis
import dev.argraur.yandex.todo.domain.model.TodoItem
import dev.argraur.yandex.todo.domain.model.Urgency
import kotlinx.datetime.LocalDate
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkTodoItem (
    val id: String,
    val text: String,
    val importance: String,
    val deadline: Long? = null,
    val done: Boolean,
    val color: String? = null,
    @SerialName("created_at") val createdAt: Long,
    @SerialName("changed_at") val changedAt: Long? = null,
    @SerialName("last_updated_by") val lastUpdatedBy: String
)

object Importance {
    const val LOW = "low"
    const val BASIC = "basic"
    const val IMPORTANT = "important"
}

fun TodoItem.toNetworkTodoItem() =
    NetworkTodoItem(
        id = id,
        text = text,
        importance = when(urgency) {
            Urgency.Low -> Importance.LOW
            Urgency.Normal -> Importance.BASIC
            Urgency.Urgent -> Importance.IMPORTANT
        },
        deadline = deadline?.toEpochMillis(),
        done = done,
        color = null,
        createdAt = creationDate.toEpochMillis(),
        changedAt = updateDate?.toEpochMillis(),
        lastUpdatedBy = "meow"
    )

fun NetworkTodoItem.toTodoItem() =
    TodoItem(
        id = id,
        text = text,
        urgency = when(importance) {
            Importance.LOW -> Urgency.Low
            Importance.BASIC -> Urgency.Normal
            Importance.IMPORTANT -> Urgency.Urgent
            else -> throw IllegalArgumentException("Unknown importance type!")
        },
        deadline = if (deadline != null) LocalDate.fromEpochMillis(deadline) else null,
        done = done,
        creationDate = LocalDate.fromEpochMillis(createdAt),
        updateDate = if (changedAt != null) LocalDate.fromEpochMillis(changedAt) else null,
    )