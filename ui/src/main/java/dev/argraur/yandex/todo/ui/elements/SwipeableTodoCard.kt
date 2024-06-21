package dev.argraur.yandex.todo.ui.elements

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.argraur.yandex.todo.domain.model.TodoItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SwipeableTodoCard(
    todoItem: TodoItem,
    onRemoveTodoItem: (TodoItem) -> Unit,
    onUpdateTodoItem: (TodoItem) -> Unit,
    onTodoItemClick: (TodoItem) -> Unit,
    modifier: Modifier = Modifier
) {
    val dismissBoxState = rememberSwipeToDismissBoxState(
        confirmValueChange = {
            when (it) {
                SwipeToDismissBoxValue.StartToEnd -> {
                    onRemoveTodoItem(todoItem)
                    return@rememberSwipeToDismissBoxState true
                }
                SwipeToDismissBoxValue.EndToStart -> {
                    onUpdateTodoItem(todoItem.copy(done = !todoItem.done))
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