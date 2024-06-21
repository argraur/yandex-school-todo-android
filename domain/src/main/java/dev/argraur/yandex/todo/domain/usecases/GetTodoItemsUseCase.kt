package dev.argraur.yandex.todo.domain.usecases

import dev.argraur.yandex.todo.domain.repository.TodoItemsRepository
import javax.inject.Inject

class GetTodoItemsUseCase @Inject constructor(
    private val repository: TodoItemsRepository
) {
    operator fun invoke() = repository.getTodoItems()
}