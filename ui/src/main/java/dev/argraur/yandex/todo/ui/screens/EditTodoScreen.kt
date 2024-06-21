package dev.argraur.yandex.todo.ui.screens

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import dev.argraur.yandex.todo.domain.model.Urgency
import dev.argraur.yandex.todo.ui.elements.DeadlineDatePicker
import dev.argraur.yandex.todo.ui.elements.UrgencyDropdownMenu
import dev.argraur.yandex.todo.ui.theme.Red
import kotlinx.datetime.toJavaLocalDate
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditTodoScreen(navController: NavController, viewModel: EditTodoScreenModel) {
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
                        navController.popBackStack()
                    }) {
                        Icon(Icons.Default.Close, "Back")
                    }
                },
                actions = {
                    TextButton(onClick = {
                        viewModel.saveTodo()
                        navController.popBackStack()
                    }) {
                        Text("Сохранить")
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
                    Text("Что надо сделать...")
                },
                modifier = Modifier.fillMaxWidth().heightIn(min = 128.dp)
            )
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Column(modifier = Modifier.clickable {
                    urgencyDropdownMenuOpened = true
                }) {
                    UrgencyDropdownMenu(
                        urgencyDropdownMenuOpened,
                        onDismissRequest = { urgencyDropdownMenuOpened = false },
                        onChoice = { urgencyDropdownMenuOpened = false; viewModel.updateUrgency(it) }
                    )
                    Text("Важность")
                    Text(
                        modifier = Modifier.alpha(0.5f),
                        text = when (todo.urgency) {
                            Urgency.Low -> "Низкий"
                            Urgency.Normal -> "Нет"
                            Urgency.Urgent -> "!! Высокий"
                        },
                        color = if (todo.urgency == Urgency.Urgent) Red else Color.Unspecified
                    )
                }
                HorizontalDivider(modifier = Modifier.fillMaxWidth())
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterStart) {
                    Column {
                        Text("Сделать до")
                        if (todo.deadline != null) {
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp), modifier = Modifier.alpha(0.5f)) {
                                Icon(Icons.Default.DateRange, "Deadline")
                                Text(
                                    text = todo.deadline!!.toJavaLocalDate()
                                        .format(
                                            DateTimeFormatter.ofPattern(
                                                "dd MMMM yyyy",
                                                Locale.getDefault()
                                            )
                                        )
                                )
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
                    onClick = { viewModel.saveTodo() },
                    enabled = !viewModel.isNew,
                    colors = ButtonDefaults.buttonColors().copy(contentColor = Red, containerColor = Color.Transparent, disabledContainerColor = Color.Transparent),
                    shape = ButtonDefaults.textShape
                ) {
                    Icon(Icons.Default.Delete, "Удалить")
                    Text("Удалить")
                }
            }
        }
    }
}