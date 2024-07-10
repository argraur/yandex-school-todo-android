package dev.argraur.yandex.todo.ui.elements

import androidx.annotation.StringRes
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.res.stringResource

@Composable
fun ErrorSnackbar(
    snackbarHostState: SnackbarHostState,
    visible: Boolean,
    @StringRes messageRes: Int,
    @StringRes actionLabelRes: Int,
    onDismiss: () -> Unit,
    onAction: () -> Unit
) {
    val message = stringResource(messageRes)
    val actionLabel = stringResource(actionLabelRes)

    LaunchedEffect(visible) {
        if (visible) {
            val result = snackbarHostState.showSnackbar(
                message,
                actionLabel,
                withDismissAction = true,
                duration = SnackbarDuration.Indefinite
            )
            when (result) {
                SnackbarResult.Dismissed -> onDismiss()
                SnackbarResult.ActionPerformed -> onAction()
            }
        }
    }
}