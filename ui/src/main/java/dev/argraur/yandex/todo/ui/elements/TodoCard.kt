package dev.argraur.yandex.todo.ui.elements

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dev.argraur.yandex.todo.domain.model.TodoItem

@Composable
fun TodoCard(todoItem: TodoItem) {
    Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = Color.Transparent), onClick = {

    }) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(start = 8.dp, end = 18.dp)) {
            Checkbox(false, onCheckedChange = { })
            Text(text = todoItem.text, maxLines = 3)
            Spacer(modifier = Modifier.weight(1f))
            Icon(Icons.Outlined.Info, "More", modifier = Modifier)
        }
    }
}