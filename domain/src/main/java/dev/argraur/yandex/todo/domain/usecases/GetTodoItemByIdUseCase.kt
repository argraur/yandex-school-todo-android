package dev.argraur.yandex.todo.domain.usecases

import dev.argraur.yandex.todo.domain.repository.TodoItemsRepository
import javax.inject.Inject

class GetTodoItemByIdUseCase @Inject constructor(
    private val repository: TodoItemsRepository
) {
    operator fun invoke(id: String) = repository.getTodoById(id)
}