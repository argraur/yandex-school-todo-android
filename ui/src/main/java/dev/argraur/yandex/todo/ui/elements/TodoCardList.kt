package dev.argraur.yandex.todo.ui.elements

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import dev.argraur.yandex.todo.domain.model.TodoItem

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TodoCardList(todoItemList: List<TodoItem>, modifier: Modifier = Modifier, onRemoveTodoItem: (TodoItem) -> Unit, onUpdateTodoItem: (TodoItem) -> Unit, onTodoItemClick: (TodoItem) -> Unit) {
    val enterTransition = fadeIn()
    val exitTransition = fadeOut()

    AnimatedVisibility(todoItemList.isNotEmpty(), enter = enterTransition, exit = exitTransition) {
        LazyColumn(
            modifier = modifier.background(
                MaterialTheme.colorScheme.surfaceColorAtElevation(2.dp),
                shape = RoundedCornerShape(12.dp)
            ),
            contentPadding = PaddingValues(vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        )
        {
            items(todoItemList, key = { it.id }) {
                SwipeableTodoCard(
                    it,
                    onRemoveTodoItem = onRemoveTodoItem,
                    onUpdateTodoItem = onUpdateTodoItem,
                    onTodoItemClick = onTodoItemClick,
                    modifier = Modifier.animateItemPlacement()
                )
            }
        }
    }

    AnimatedVisibility(todoItemList.isEmpty(), enter = enterTransition, exit = exitTransition) {
        Box(modifier = modifier.fillMaxHeight(), contentAlignment = Alignment.Center) {
            Text("Тут ничего нет :(")
        }
    }
}