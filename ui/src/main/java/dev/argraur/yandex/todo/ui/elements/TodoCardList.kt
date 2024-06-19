package dev.argraur.yandex.todo.ui.elements

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.argraur.yandex.todo.domain.model.TodoItem

@Composable
fun TodoCardList(todoItemList: List<TodoItem>, modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier.background(MaterialTheme.colorScheme.surfaceColorAtElevation(2.dp),
        shape = RoundedCornerShape(24.dp)),
        contentPadding = PaddingValues(vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp))
    {
        items(todoItemList) {
            TodoCard(it)
        }
    }
}