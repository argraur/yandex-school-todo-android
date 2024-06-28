package dev.argraur.yandex.todo.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color

@Immutable
data class CustomColorsPalette(
    val redAccent: Color = Color.Unspecified,
    val greenAccent: Color = Color.Unspecified,
    val blueAccent: Color = Color.Unspecified,
    val grayAccent: Color = Color.Unspecified,
    val grayLightAccent: Color = Color.Unspecified,
    val whiteAccent: Color = Color.Unspecified
)

val LocalCustomColorsPalette = compositionLocalOf { CustomColorsPalette() }