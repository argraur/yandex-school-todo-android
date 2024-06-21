package dev.argraur.yandex.todo.ui.elements

import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import dev.argraur.yandex.todo.domain.model.Urgency
import dev.argraur.yandex.todo.ui.theme.Red

@Composable
fun UrgencyDropdownMenu(expanded: Boolean, onDismissRequest: () -> Unit, onChoice: (Urgency) -> Unit) {
    DropdownMenu(expanded, onDismissRequest) {
        DropdownMenuItem(
            text = { Text("Нет") },
            onClick = { onChoice(Urgency.Normal) }
        )
        DropdownMenuItem(
            text = { Text("Низкий") },
            onClick = { onChoice(Urgency.Low) }
        )
        DropdownMenuItem(
            text = { Text("!! Высокий", color = Red) },
            onClick = { onChoice(Urgency.Urgent) }
        )
    }
}