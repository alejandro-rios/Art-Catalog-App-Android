package com.alejandrorios.art_catalog_app.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = DarkPrimary,
    secondary = DarkSecondary,
    secondaryContainer = DarkSurface,
    tertiary = DarkOnSurface,
    surface = DarkSurface,
    onSurface = DarkPrimary,
    surfaceVariant = DarkOnSurface,
    onSurfaceVariant = DarkPrimary,
    background = DarkBackground,
)

private val LightColorScheme = lightColorScheme(
    primary = LightPrimary,
    secondary = LightSecondary,
    secondaryContainer = LightSurface,
    tertiary = LightOnSurface,
    surface = LightSurface,
    onSurface = LightPrimary,
    surfaceVariant = LightOnSurface,
    onSurfaceVariant = LightPrimary,
    background = LightBackground,
)

@Composable
fun Art_catalog_appTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
