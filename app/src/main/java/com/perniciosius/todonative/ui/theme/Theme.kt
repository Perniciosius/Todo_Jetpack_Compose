package com.perniciosius.todonative.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = Blue300,
    primaryVariant = Blue700,
    secondary = Pink700,
    background = Blue200,
    surface = Blue200,
    onPrimary = White200,
    onBackground = White200,
    onSecondary = White200,
    onSurface = White200
)

private val LightColorPalette = lightColors(
    primary = White200,
    primaryVariant = White200,
    secondary = Blue300,
    background = White200,
    surface = White200,
    onPrimary = Color.Black,
    onSecondary = Color.Black,
    onSurface = Color.Black,
    onBackground = Color.Black,
)

@Composable
fun ToDoNativeTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable() () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}