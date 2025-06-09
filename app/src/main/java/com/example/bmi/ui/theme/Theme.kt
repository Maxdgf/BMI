package com.example.bmi.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = black,
    onPrimary = white,
    secondary = black,
    onSecondary = white,
    background = white,
    onBackground = black,
    surface = white,
    onSurface = black
)

private val LightColorScheme = lightColorScheme(
    primary = blue,
    onPrimary = white,
    secondary = blue,
    onSecondary = white,
    background = white,
    onBackground = blue,
    surface = white,
    onSurface = blue
)

@Composable
fun BMITheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) {
        DarkColorScheme
    } else {
        LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}