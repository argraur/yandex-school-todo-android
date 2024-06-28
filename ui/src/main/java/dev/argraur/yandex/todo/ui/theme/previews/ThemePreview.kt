package dev.argraur.yandex.todo.ui.theme.previews

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import dev.argraur.yandex.todo.ui.theme.LocalCustomColorsPalette
import dev.argraur.yandex.todo.ui.theme.TodoTheme
import dev.argraur.yandex.todo.ui.theme.Typography

@PreviewLightDark
@Composable
fun ThemePreview() {
    TodoTheme {
        Column(verticalArrangement = Arrangement.spacedBy(24.dp)) {
            Surface {
                Column {
                    Text("Large title", style = Typography.titleLarge)
                    Text("Title", style = Typography.titleSmall)
                    Text("Body", style = Typography.bodyMedium)
                    Text("Subhead", style = Typography.headlineSmall)
                }
            }
            Column {
                val colors = listOf(
                    MaterialTheme.colorScheme.background,
                    MaterialTheme.colorScheme.primary,
                    MaterialTheme.colorScheme.secondary,
                    MaterialTheme.colorScheme.tertiary,
                    MaterialTheme.colorScheme.onBackground,
                    MaterialTheme.colorScheme.onPrimary,
                    MaterialTheme.colorScheme.onSecondary,
                    MaterialTheme.colorScheme.onTertiary,
                    MaterialTheme.colorScheme.surface,
                    MaterialTheme.colorScheme.surfaceVariant,
                    MaterialTheme.colorScheme.surfaceContainer,
                    MaterialTheme.colorScheme.onSurface,
                    MaterialTheme.colorScheme.onSurfaceVariant,
                    MaterialTheme.colorScheme.surfaceColorAtElevation(2.dp),
                    LocalCustomColorsPalette.current.redAccent,
                    LocalCustomColorsPalette.current.blueAccent,
                    LocalCustomColorsPalette.current.greenAccent,
                    LocalCustomColorsPalette.current.grayAccent,
                    LocalCustomColorsPalette.current.grayLightAccent,
                    LocalCustomColorsPalette.current.whiteAccent
                )
                LazyVerticalGrid(GridCells.Fixed(3)) {
                    items(colors) {
                        Box(modifier = Modifier.width(64.dp).height(48.dp).background(it))
                    }
                }
            }
        }
    }
}