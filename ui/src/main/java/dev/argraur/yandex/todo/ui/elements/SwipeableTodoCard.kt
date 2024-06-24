package dev.argraur.yandex.todo.ui.elements

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import dev.argraur.yandex.todo.domain.model.TodoItem
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SwipeableTodoCard(
    todoItem: TodoItem,
    onRemoveTodoItem: (TodoItem) -> Unit,
    onUpdateTodoItem: (TodoItem) -> Unit,
    onTodoItemClick: (TodoItem) -> Unit,
    modifier: Modifier = Modifier
) {
    var shouldRemove by remember { mutableStateOf(false) }
    LaunchedEffect(shouldRemove) {
        if (shouldRemove) {
            delay(300)
            onRemoveTodoItem(todoItem)
            shouldRemove = false
        }
    }
    val dismissBoxState = rememberSwipeToDismissBoxState(
        confirmValueChange = {
            when (it) {
                SwipeToDismissBoxValue.StartToEnd -> {
                    if (!shouldRemove)
                        shouldRemove = true
                    return@rememberSwipeToDismissBoxState true
                }
                SwipeToDismissBoxValue.EndToStart -> {
                    onUpdateTodoItem(todoItem.copy(done = true))
                }
                SwipeToDismissBoxValue.Settled -> {}
            }
            false
        },
        positionalThreshold = { it * .25f }
    )
    SwipeToDismissBox(
        dismissBoxState,
        modifier = modifier,
        backgroundContent = { DismissBackground(dismissBoxState) },
        content = {
            TodoCard(todoItem, onUpdateTodoItem = onUpdateTodoItem, onTodoItemClick = onTodoItemClick, modifier)
        }
    )
}