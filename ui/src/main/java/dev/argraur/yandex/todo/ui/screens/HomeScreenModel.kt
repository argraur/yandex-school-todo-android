package dev.argraur.yandex.todo.ui.screens

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.argraur.yandex.todo.domain.model.TodoItem
import dev.argraur.yandex.todo.domain.usecases.GetTodoItemsUseCase
import dev.argraur.yandex.todo.domain.usecases.RemoveTodoItemUseCase
import dev.argraur.yandex.todo.domain.usecases.UpdateTodoItemUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

sealed class HomeScreenUiState {
    // For future app expansions
    data class Loaded(
        val doneVisible: Boolean
    ): HomeScreenUiState()
}

@HiltViewModel
class HomeScreenModel @Inject constructor(
    getTodoItemsUseCase: GetTodoItemsUseCase,
    private val updateTodoItemUseCase: UpdateTodoItemUseCase,
    private val removeTodoItemUseCase: RemoveTodoItemUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow<HomeScreenUiState>(HomeScreenUiState.Loaded(false))
    val uiState: StateFlow<HomeScreenUiState>
        get() = _uiState.asStateFlow()

    val todoItems: StateFlow<List<TodoItem>> = getTodoItemsUseCase()

    fun updateTodoItem(item: TodoItem) {
        updateTodoItemUseCase(item)
    }

    fun removeTodoItem(item: TodoItem) {
        removeTodoItemUseCase(item.id)
    }

    fun toggleVisibility() {
        _uiState.update {
            if (it is HomeScreenUiState.Loaded)
                it.copy(!it.doneVisible)
            else
                it
        }
    }
}