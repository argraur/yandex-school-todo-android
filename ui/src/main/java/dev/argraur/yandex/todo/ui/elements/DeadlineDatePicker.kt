package dev.argraur.yandex.todo.ui.elements

import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeadlineDatePicker(opened: Boolean, onDismissRequest: () -> Unit, onConfirm: (Long?) -> Unit) {
    if (opened) {
        val datePickerState = rememberDatePickerState()
        val confirmEnabled by remember {
            derivedStateOf {
                datePickerState.selectedDateMillis != null
            }
        }
        DatePickerDialog(
            onDismissRequest = {
                onDismissRequest()
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        onDismissRequest()
                        onConfirm(datePickerState.selectedDateMillis)
                    },
                    enabled = confirmEnabled
                ) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    onDismissRequest()
                }) {
                    Text("Отмена")
                }
            }
        ) {
            DatePicker(datePickerState)
        }
    }
}