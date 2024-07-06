package dev.argraur.yandex.todo.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import dev.argraur.yandex.todo.core.extensions.localizedFormat
import dev.argraur.yandex.todo.domain.model.Urgency
import dev.argraur.yandex.todo.ui.R
import dev.argraur.yandex.todo.ui.elements.DeadlineDatePicker
import dev.argraur.yandex.todo.ui.elements.UrgencyDropdownMenu
import dev.argraur.yandex.todo.ui.theme.LocalCustomColorsPalette

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditTodoScreen(viewModel: EditTodoScreenModel, onNavigateBack: () -> Unit) {
    val todo by viewModel.todo.collectAsState()
    val deadlineEnabled = todo.deadline != null

    var urgencyDropdownMenuOpened by remember { mutableStateOf(false) }
    var datePickerOpened by remember { mutableStateOf(false) }

    DeadlineDatePicker(
        datePickerOpened,
        onDismissRequest = {
            datePickerOpened = false
        },
        onConfirm = {
            if (it != null)
                viewModel.updateDeadline(it)
        }
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton({
                        onNavigateBack()
                    }) {
                        Icon(Icons.Default.Close,
                            stringResource(R.string.content_description_close_editor))
                    }
                },
                actions = {
                    TextButton(onClick = {
                        viewModel.saveTodo()
                        onNavigateBack()
                    }) {
                        Text(stringResource(R.string.edit_todo_save))
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 12.dp, vertical = 12.dp)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            TextField(
                value = todo.text,
                onValueChange = {
                    viewModel.updateText(it)
                },
                placeholder = {
                    Text(stringResource(R.string.edit_todo_text_placeholder))
                },
                modifier = Modifier.fillMaxWidth().heightIn(min = 128.dp)
            )
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Column(modifier = Modifier.clickable {
                    urgencyDropdownMenuOpened = true
                }.fillMaxWidth()) {
                    UrgencyDropdownMenu(
                        urgencyDropdownMenuOpened,
                        onDismissRequest = { urgencyDropdownMenuOpened = false },
                        onChoice = { urgencyDropdownMenuOpened = false; viewModel.updateUrgency(it) }
                    )
                    Text(stringResource(R.string.edit_todo_urgency))
                    Text(
                        modifier = Modifier.alpha(0.5f),
                        text = when (todo.urgency) {
                            Urgency.Low -> stringResource(R.string.edit_todo_urgency_low)
                            Urgency.Normal -> stringResource(R.string.edit_todo_urgency_normal)
                            Urgency.Urgent -> stringResource(R.string.edit_todo_urgency_urgent)
                        },
                        color = if (todo.urgency == Urgency.Urgent) LocalCustomColorsPalette.current.redAccent else Color.Unspecified
                    )
                }
                HorizontalDivider(modifier = Modifier.fillMaxWidth())
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterStart) {
                    Column(modifier = Modifier.clickable {
                        if (deadlineEnabled)
                            datePickerOpened = true
                    }) {
                        Text(stringResource(R.string.edit_todo_deadline))
                        if (todo.deadline != null) {
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp), modifier = Modifier.alpha(0.5f)) {
                                Icon(Icons.Default.DateRange,
                                    stringResource(R.string.content_description_deadline))
                                Text(text = todo.deadline!!.localizedFormat())
                            }
                        }
                    }
                    Switch(
                        checked = deadlineEnabled,
                        onCheckedChange = {
                            if (!deadlineEnabled)
                                datePickerOpened = true
                            else {
                                viewModel.updateDeadline(null)
                            }
                        },
                        modifier = Modifier.align(Alignment.CenterEnd)
                    )
                }
                HorizontalDivider(modifier = Modifier.fillMaxWidth())
                Button(
                    onClick = { viewModel.removeTodo(); onNavigateBack() },
                    enabled = !viewModel.isNew,
                    colors = ButtonDefaults.buttonColors().copy(contentColor = LocalCustomColorsPalette.current.redAccent, containerColor = Color.Transparent, disabledContainerColor = Color.Transparent),
                    shape = ButtonDefaults.textShape
                ) {
                    Icon(Icons.Default.Delete, stringResource(R.string.content_description_delete))
                    Text(stringResource(R.string.edit_todo_delete))
                }
            }
        }
    }
}