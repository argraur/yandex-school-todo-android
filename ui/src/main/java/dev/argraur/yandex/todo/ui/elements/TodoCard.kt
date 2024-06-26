package dev.argraur.yandex.todo.ui.elements

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import dev.argraur.yandex.todo.core.extensions.localizedFormat
import dev.argraur.yandex.todo.domain.model.TodoItem
import dev.argraur.yandex.todo.domain.model.Urgency
import dev.argraur.yandex.todo.ui.R
import dev.argraur.yandex.todo.ui.theme.Green
import dev.argraur.yandex.todo.ui.theme.Red
import kotlinx.datetime.toJavaLocalDate
import kotlinx.datetime.toKotlinLocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun TodoCard(todoItem: TodoItem, onUpdateTodoItem: (TodoItem) -> Unit, onTodoItemClick: (TodoItem) -> Unit, modifier: Modifier) {
    val textAlpha = if (todoItem.done) 0.5f else 1f
    var text = todoItem.text
    val textDecoration = if (todoItem.done) TextDecoration.LineThrough else TextDecoration.None
    var uncheckedColor = Color.Unspecified
    when (todoItem.urgency) {
        Urgency.Low -> text = stringResource(R.string.todo_card_urgency_low_format, text)
        Urgency.Urgent -> { text = stringResource(R.string.todo_card_urgency_urgent_format, text); uncheckedColor = Color.Red }
        else -> {}
    }
    var deadlineMiss = todoItem.deadline != null && java.time.LocalDate.now().toKotlinLocalDate() > todoItem.deadline!!

    Card(modifier = modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(2.dp)), onClick = { onTodoItemClick(todoItem) }, shape = RectangleShape) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(start = 8.dp, end = 8.dp)) {
            Checkbox(todoItem.done, onCheckedChange = { onUpdateTodoItem(todoItem.copy(done = it)) }, modifier = Modifier.weight(0.1f), colors = CheckboxDefaults.colors(checkedColor = Green, uncheckedColor = uncheckedColor))
            Column(modifier = Modifier.weight(0.8f)) {
                Text(
                    text = text,
                    maxLines = 3,
                    modifier = Modifier.alpha(textAlpha),
                    overflow = TextOverflow.Ellipsis,
                    textDecoration = textDecoration
                )
                if (todoItem.deadline != null) {
                    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.alpha(0.5f), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        Icon(
                            Icons.Default.DateRange,
                            stringResource(R.string.content_description_deadline),
                            modifier = Modifier.size(16.dp),
                            tint = if (deadlineMiss) Red else LocalContentColor.current
                        )
                        Text(
                            color = if (deadlineMiss) Red else Color.Unspecified,
                            text = todoItem.deadline!!.localizedFormat()
                        )
                    }
                }
            }
            Icon(Icons.Outlined.Info,
                stringResource(R.string.content_description_info), modifier = Modifier.weight(0.1f))
        }
    }
}