package dev.argraur.yandex.todo.domain.usecases

import dev.argraur.yandex.todo.domain.model.TodoItem
import dev.argraur.yandex.todo.domain.repository.TodoItemsRepository
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class UpdateTodoItemUseCase @Inject constructor(
    private val repository: TodoItemsRepository
) {
    operator fun invoke(item: TodoItem) = repository.updateTodoItem(item)
}