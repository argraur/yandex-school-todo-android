package dev.argraur.yandex.todo.domain.usecases

import dev.argraur.yandex.todo.domain.model.TodoItem
import dev.argraur.yandex.todo.domain.repository.TodoItemsRepository
import kotlinx.datetime.toKotlinLocalDate
import java.time.LocalDate
import javax.inject.Inject

class AddTodoItemUseCase @Inject constructor(
    private val repository: TodoItemsRepository
) {
    suspend operator fun invoke(item: TodoItem) = repository.addTodoItem(item.copy(creationDate = LocalDate.now().toKotlinLocalDate()))
}