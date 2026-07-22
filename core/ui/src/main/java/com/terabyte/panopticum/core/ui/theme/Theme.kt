package com.terabyte.panopticum.core.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColors = lightColorScheme(
    primary = ColorPrimary,
    onPrimary = ColorOnPrimary,
    secondary = ColorSecondary,
    error = ColorError,
    background = ColorBackground,
    surface = ColorSurface,
    onBackground = ColorOnBackground,
    onSurface = ColorOnSurface
)

//private val DarkColors = darkColorScheme(
//    primary = ColorPrimary,
//    onPrimary = ColorOnPrimary,
//    secondary = ColorSecondary,
//    error = ColorError,
//    background = ColorBackground,
//    surface = ColorSurface,
//    onBackground = ColorOnBackground,
//    onSurface = ColorOnSurface
//)

@Composable
fun AppTheme(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LightColors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}
