package dev.argraur.yandex.todo.ui.screens.previews

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.PreviewLightDark
import dev.argraur.yandex.todo.domain.model.TodoItem
import dev.argraur.yandex.todo.domain.model.Urgency
import dev.argraur.yandex.todo.domain.repository.TodoItemsRepository
import dev.argraur.yandex.todo.domain.usecases.GetTodoItemsUseCase
import dev.argraur.yandex.todo.domain.usecases.RemoveTodoItemUseCase
import dev.argraur.yandex.todo.domain.usecases.UpdateTodoItemUseCase
import dev.argraur.yandex.todo.ui.screens.HomeScreen
import dev.argraur.yandex.todo.ui.screens.HomeScreenModel
import dev.argraur.yandex.todo.ui.theme.TodoTheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.datetime.LocalDate

@PreviewLightDark
@Composable
fun HomePreview() {
    val viewModel = HomeScreenModel(
        getTodoItemsUseCase = GetTodoItemsUseCase(repository),
        updateTodoItemUseCase = UpdateTodoItemUseCase(repository),
        removeTodoItemUseCase = RemoveTodoItemUseCase(repository),
    )

    viewModel.toggleVisibility()

    TodoTheme {
        HomeScreen(
            viewModel = viewModel,
            onNavigateToNew = {},
            onNavigateToEdit = {}
        )
    }
}

val repository: TodoItemsRepository = object : TodoItemsRepository {
    override fun getTodoItems(): StateFlow<List<TodoItem>> =
        MutableStateFlow(
            listOf(
                TodoItem(
                    "1",
                    "Preview task 1",
                    Urgency.Urgent,
                    true,
                    LocalDate.fromEpochDays(0),
                ),
                TodoItem(
                    "2",
                    "Preview task 2",
                    Urgency.Low,
                    false,
                    LocalDate.fromEpochDays(0),
                    deadline = LocalDate.fromEpochDays(1)
                ),
            )
        )

    override suspend fun addTodoItem(item: TodoItem): Boolean {
        TODO("Not yet implemented")
    }

    override fun getTodoById(id: String): TodoItem {
        TODO("Not yet implemented")
    }

    override suspend fun removeTodoItem(id: String): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun updateTodoItem(item: TodoItem): Boolean {
        TODO("Not yet implemented")
    }
}
