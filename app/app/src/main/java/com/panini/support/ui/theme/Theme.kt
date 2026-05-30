package com.panini.support.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = PaniniYellow,
    secondary = PaniniSurfaceAlt,
    tertiary = PaniniRed,
    background = PaniniBlueDark,
    surface = Color(0xFF142B5F),
    surfaceVariant = Color(0xFF223C76),
    onPrimary = PaniniBlueDark,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color.White,
    onSurface = Color.White,
    onSurfaceVariant = Color.White
)

private val LightColorScheme = lightColorScheme(
    primary = PaniniBlue,
    secondary = PaniniYellow,
    tertiary = PaniniRed,
    background = PaniniBackground,
    surface = PaniniSurface,
    surfaceVariant = PaniniSurfaceAlt,
    onPrimary = Color.White,
    onSecondary = PaniniBlueDark,
    onTertiary = Color.White,
    onBackground = PaniniText,
    onSurface = PaniniText,
    onSurfaceVariant = PaniniText
)

@Composable
fun PaniniSupportTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}