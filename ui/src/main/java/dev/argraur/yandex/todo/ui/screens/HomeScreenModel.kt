package dev.argraur.yandex.todo.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.argraur.yandex.todo.domain.model.TodoItem
import dev.argraur.yandex.todo.domain.usecases.GetTodoItemsUseCase
import dev.argraur.yandex.todo.domain.usecases.RemoveTodoItemUseCase
import dev.argraur.yandex.todo.domain.usecases.UpdateTodoItemUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class HomeScreenUiState {
    // For future app expansions
    data object Loading: HomeScreenUiState()
    data class Loaded(
        val doneVisible: Boolean = false,
        val isError: Boolean = false,
        val errorMsg: String = ""
    ): HomeScreenUiState()
    data class LoadingError(
        val errorMsg: String = ""
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

    var todoItems: StateFlow<List<TodoItem>> = getTodoItemsUseCase()

    val coroutineContext = Dispatchers.Main.immediate

    init {
        refreshRepository()
    }

    // For future expansion
    fun refreshRepository() {
        _uiState.update { HomeScreenUiState.Loading }
        // Update repository
        // onLoadingError()
        _uiState.update { HomeScreenUiState.Loaded() }
    }

    fun updateTodoItem(item: TodoItem) = viewModelScope.launch {
        val result = updateTodoItemUseCase(item)
        if (!result) {
            onError("Failed to update task")
        }
    }

    fun removeTodoItem(item: TodoItem) = viewModelScope.launch {
        val result = removeTodoItemUseCase(item.id)
        if (!result) {
            onError("Failed to remove task")
        }
    }

    fun toggleVisibility() = viewModelScope.launch(coroutineContext) {
        _uiState.update {
            if (it is HomeScreenUiState.Loaded)
                it.copy(!it.doneVisible)
            else
                it
        }
    }

    private fun onError(errorMsg: String) = viewModelScope.launch(coroutineContext) {
        _uiState.update {
            if (it is HomeScreenUiState.Loaded) {
                it.copy(isError = true, errorMsg = errorMsg)
            } else {
                it
            }
        }
    }

    private fun onLoadingError(errorMsg: String) = viewModelScope.launch(coroutineContext) {
        _uiState.update { HomeScreenUiState.LoadingError(errorMsg) }
    }
}