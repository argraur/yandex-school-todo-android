package dev.argraur.yandex.todo.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.argraur.yandex.todo.domain.model.TodoItem
import dev.argraur.yandex.todo.domain.usecases.GetTodoItemsUseCase
import dev.argraur.yandex.todo.domain.usecases.RefreshRepositoryUseCase
import dev.argraur.yandex.todo.domain.usecases.RemoveTodoItemUseCase
import dev.argraur.yandex.todo.domain.usecases.UpdateTodoItemUseCase
import dev.argraur.yandex.todo.ui.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class HomeScreenUiState (
    val todoItems: Flow<List<TodoItem>> = flowOf(),
    val doneVisible: Boolean = false,
    val isError: Boolean = false,
    val errorMsg: Int = R.string.error_no_error,
    val isRefreshing: Boolean = false
)

@HiltViewModel
class HomeScreenModel @Inject constructor(
    private val getTodoItemsUseCase: GetTodoItemsUseCase,
    private val updateTodoItemUseCase: UpdateTodoItemUseCase,
    private val removeTodoItemUseCase: RemoveTodoItemUseCase,
    private val refreshRepositoryUseCase: RefreshRepositoryUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeScreenUiState())
    val uiState: StateFlow<HomeScreenUiState>
        get() = _uiState.asStateFlow()

    private val coroutineContext = Dispatchers.Main.immediate

    init {
        refreshRepository()
    }

    fun refreshRepository() {
        viewModelScope.launch {
            _uiState.update { it.copy(isRefreshing = true) }
            runCatching {
                refreshRepositoryUseCase()
            }.onFailure {
                _uiState.update { it.copy(todoItems = getTodoItemsUseCase(), isError = true, errorMsg = R.string.error_server_sync, isRefreshing = false) }
                return@launch
            }.onSuccess {
                _uiState.update { it.copy(todoItems = getTodoItemsUseCase(), isRefreshing = false) }
            }
        }
    }

    fun updateTodoItem(item: TodoItem) = viewModelScope.launch {
        runCatching {
            updateTodoItemUseCase(item)
        }.onFailure {
            onError(R.string.error_task_update)
        }
    }

    fun removeTodoItem(item: TodoItem) = viewModelScope.launch {
        runCatching {
            removeTodoItemUseCase(item.id)
        }.onFailure {
            onError(R.string.error_task_remove)
        }
    }

    fun toggleVisibility() = viewModelScope.launch(coroutineContext) {
        _uiState.update {
            it.copy(doneVisible = !it.doneVisible)
        }
    }

    fun dismissError() = viewModelScope.launch(coroutineContext) {
        _uiState.update {
            it.copy(isError = false, errorMsg = R.string.error_no_error)
        }
    }

    private fun onError(errorMsg: Int) = viewModelScope.launch(coroutineContext) {
        _uiState.update {
            it.copy(isError = true, errorMsg = errorMsg)
        }
    }
}