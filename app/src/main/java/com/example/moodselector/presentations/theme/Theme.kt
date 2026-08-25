package com.example.moodselector.presentations.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(

    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80,

    background = Color(0xFF201A36),
    surface = Color(0xFF2A2346),
    surfaceVariant = Color(0xFF393155),

    onBackground = Color(0xFFF4F0FA),
    onSurface = Color(0xFFF4F0FA),
    onSurfaceVariant = Color(0xFFD7D0E5),

    onPrimary = Color(0xFF30284A),
    onSecondary = Color(0xFF312B3B),
    onTertiary = Color(0xFF3A2530)
)

private val LightColorScheme = lightColorScheme(

    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40,

    background = Color(0xFFF8F5FC),
    surface = Color(0xFFFFFBFF),
    surfaceVariant = Color(0xFFEDE7F5),

    onBackground = Color(0xFF1F1C24),
    onSurface = Color(0xFF1F1C24),
    onSurfaceVariant = Color(0xFF625C68),

    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White
)

@Composable
fun MoodselectorTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {

    val colorScheme = when {

        darkTheme -> {

            DarkColorScheme
        }

        dynamicColor &&
                Build.VERSION.SDK_INT >=
                Build.VERSION_CODES.S -> {

            dynamicLightColorScheme(
                LocalContext.current
            )
        }

        else -> {

            LightColorScheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}