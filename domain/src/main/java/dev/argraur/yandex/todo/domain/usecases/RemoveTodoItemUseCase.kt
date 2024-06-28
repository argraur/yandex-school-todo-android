package dev.argraur.yandex.todo.domain.usecases

import dev.argraur.yandex.todo.domain.repository.TodoItemsRepository
import javax.inject.Inject

class RemoveTodoItemUseCase @Inject constructor(
    private val repository: TodoItemsRepository
) {
    suspend operator fun invoke(id: String) = repository.removeTodoItem(id)
}