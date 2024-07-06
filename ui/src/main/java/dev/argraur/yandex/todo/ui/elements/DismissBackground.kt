package dev.argraur.yandex.todo.ui.elements

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SwipeToDismissBoxState
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import dev.argraur.yandex.todo.ui.R
import dev.argraur.yandex.todo.ui.theme.LocalCustomColorsPalette

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun DismissBackground(dismissBoxState: SwipeToDismissBoxState) {
    val color = when (dismissBoxState.dismissDirection) {
        SwipeToDismissBoxValue.StartToEnd -> LocalCustomColorsPalette.current.redAccent
        SwipeToDismissBoxValue.EndToStart -> LocalCustomColorsPalette.current.greenAccent
        SwipeToDismissBoxValue.Settled -> Color.Transparent
    }
    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(color)
            .padding(12.dp, 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        AnimatedVisibility(dismissBoxState.dismissDirection == SwipeToDismissBoxValue.StartToEnd) {
            Icon(
                Icons.Default.Delete,
                contentDescription = stringResource(R.string.content_description_delete)
            )
        }
        Spacer(modifier = Modifier)
        AnimatedVisibility(dismissBoxState.dismissDirection == SwipeToDismissBoxValue.EndToStart) {
            Icon(
                Icons.Default.Done,
                contentDescription = stringResource(R.string.content_description_done)
            )
        }
    }
}