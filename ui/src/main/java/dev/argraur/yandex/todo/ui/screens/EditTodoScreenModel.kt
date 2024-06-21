package dev.argraur.yandex.todo.ui.screens

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.argraur.yandex.todo.domain.model.TodoItem
import dev.argraur.yandex.todo.domain.model.Urgency
import dev.argraur.yandex.todo.domain.usecases.AddTodoItemUseCase
import dev.argraur.yandex.todo.domain.usecases.GetTodoItemByIdUseCase
import dev.argraur.yandex.todo.domain.usecases.UpdateTodoItemUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.datetime.LocalDate

import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class EditTodoScreenModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    getTodoItemByIdUseCase: GetTodoItemByIdUseCase,
    private val addTodoItemUseCase: AddTodoItemUseCase,
    private val updateTodoItemUseCase: UpdateTodoItemUseCase
): ViewModel() {
    private val id: String? = savedStateHandle["id"]
    private val _todo: MutableStateFlow<TodoItem>
    val todo: StateFlow<TodoItem>
        get() = _todo.asStateFlow()

    val isNew: Boolean
        get() = id == null

    init {
        _todo = if (id != null)
            MutableStateFlow(getTodoItemByIdUseCase(id))
        else
            MutableStateFlow(TodoItem(UUID.randomUUID().toString(), "", Urgency.Normal, false))
    }

    fun saveTodo() {
        if (id != null) {
            updateTodoItemUseCase(_todo.value)
        } else {
            addTodoItemUseCase(_todo.value)
        }
    }

    fun updateText(text: String) {
        _todo.update {
            it.copy(text = text)
        }
    }

    fun updateUrgency(urgency: Urgency) {
        _todo.update {
            it.copy(urgency = urgency)
        }
    }

    fun updateDeadline(dateMillis: Long?) {
        _todo.update {
            it.copy(
                deadline =
                if (dateMillis != null)
                    LocalDate.fromEpochDays((dateMillis / 1000 / 60 / 60 / 24).toInt())
                else null
            )
        }
    }
}