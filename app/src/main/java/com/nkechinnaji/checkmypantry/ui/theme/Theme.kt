package com.nkechinnaji.checkmypantry.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF5C6BC0),          // Indigo 400 - Calm blue-purple
    onPrimary = Color.White,
    secondary = Color(0xFFAB47BC),        // Purple 400 - Vibrant purple accent
    onSecondary = Color.White,
    background = Color.White,       // Lavender Blush - very light purple background
    onBackground = Color(0xFF311B92),     // Indigo 900 - Dark strong contrast
    surface = Color(0xFFF527EB),          // Indigo 50 - Soft surface background
    onSurface = Color(0xFF1A237E),        // Indigo 900 - Strong text on surface
    surfaceVariant = Color(0xFF000000),   // Light purple for snackbars & cards
    onSurfaceVariant = Color(0xFF512DA8), // Deep purple text for variants
    error = Color(0xFFD32F2F),            // Red 700 - standard error color
    onError = Color.White,
    tertiary = Color(0xFFFFF176),
    onTertiary = Color(0xFF27B4F5)
)



@Composable
fun CheckMyPantryTheme(
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,  // Optional: can customize
        content = content
    )
}
