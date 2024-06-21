package dev.argraur.yandex.todo.domain.model

import kotlinx.datetime.LocalDate

data class TodoItem(
    val id: String,
    val text: String,
    val urgency: Urgency,
    val done: Boolean,
    val creationDate: LocalDate? = null,
    val updateDate: LocalDate? = null,
    val deadline: LocalDate? = null
)

enum class Urgency {
    Low, Normal, Urgent
}