package dev.argraur.yandex.todo.ui.screens

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.argraur.yandex.todo.core.extensions.fromEpochMillis
import dev.argraur.yandex.todo.domain.model.TodoItem
import dev.argraur.yandex.todo.domain.model.Urgency
import dev.argraur.yandex.todo.domain.usecases.AddTodoItemUseCase
import dev.argraur.yandex.todo.domain.usecases.GetTodoItemByIdUseCase
import dev.argraur.yandex.todo.domain.usecases.RemoveTodoItemUseCase
import dev.argraur.yandex.todo.domain.usecases.UpdateTodoItemUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate

import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class EditTodoScreenModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    getTodoItemByIdUseCase: GetTodoItemByIdUseCase,
    private val addTodoItemUseCase: AddTodoItemUseCase,
    private val updateTodoItemUseCase: UpdateTodoItemUseCase,
    private val removeTodoItemUseCase: RemoveTodoItemUseCase
): ViewModel() {
    private val id: String? = savedStateHandle["id"]
    private val _todo: MutableStateFlow<TodoItem> by lazy {
        if (id != null)
            MutableStateFlow(getTodoItemByIdUseCase(id))
        else
            MutableStateFlow(
                TodoItem(
                    UUID.randomUUID().toString(),
                    "",
                    Urgency.Normal,
                    false,
                    creationDate = LocalDate.fromEpochDays(0)
                )
            )
    }
    val todo: StateFlow<TodoItem>
        get() = _todo.asStateFlow()

    val isNew: Boolean
        get() = id == null

    init {
        viewModelScope.launch(Dispatchers.Main.immediate) {

        }
    }

    fun saveTodo() = viewModelScope.launch {
        if (id != null) {
            updateTodoItemUseCase(_todo.value)
        } else {
            addTodoItemUseCase(_todo.value)
        }
    }

    fun updateText(text: String) = viewModelScope.launch(Dispatchers.Main.immediate) {
        _todo.update {
            it.copy(text = text)
        }
    }

    fun updateUrgency(urgency: Urgency) = viewModelScope.launch(Dispatchers.Main.immediate) {
        _todo.update {
            it.copy(urgency = urgency)
        }
    }

    fun updateDeadline(dateMillis: Long?) = viewModelScope.launch(Dispatchers.Main.immediate) {
        _todo.update {
            it.copy(
                deadline =
                    if (dateMillis != null)
                        LocalDate.fromEpochMillis(dateMillis)
                    else null
            )
        }
    }

    fun removeTodo() = viewModelScope.launch {
        if (!isNew)
            removeTodoItemUseCase(id!!)
    }
}