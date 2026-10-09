package com.jruizdev.rickdex.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = yellowToolbar,
    onPrimary = pinkVibrantAccent,
    primaryContainer = Color(0xFF2B4D00),
    onPrimaryContainer = PortalGreenBright,
    secondary = RickCyan,
    onSecondary = Color(0xFF00363D),
    secondaryContainer = Color(0xFF004F58),
    onSecondaryContainer = RickCyanLight,
    tertiary = MortyYellow,
    onTertiary = Color(0xFF3A3200),
    tertiaryContainer = Color(0xFF534900),
    onTertiaryContainer = Color(0xFFFFF078),
    background = backgroundWine,
    onBackground = SpaceTextPrimaryDark,
    surface = RickGreen,
    onSurface = MortyYellow,
    surfaceVariant = SpaceSurfaceVariantDark,
    onSurfaceVariant = SpaceTextSecondaryDark,
    outline = MultiversePink,
    error = Color(0xFFFF6B6B),
    errorContainer = Color(0xFF491212),
    onError = Color(0xFF600000),
    onErrorContainer = Color(0xFFFFDAD6)
)

private val LightColorScheme = lightColorScheme(
    primary = PortalGreenDark,
    onPrimary = Color.White,
    primaryContainer = PortalGreenBright,
    onPrimaryContainer = Color(0xFF0F2000),
    secondary = RickCyanDark,
    onSecondary = Color.White,
    secondaryContainer = DimensionPurple,
    onSecondaryContainer = Color(0xFF001F24),
    tertiary = MortyYellowDark,
    onTertiary = Color.White,
    tertiaryContainer = MortyYellow,
    onTertiaryContainer = Color(0xFF201B00),
    background = SciFiBackgroundLight,
    onBackground = Color(0xFF141A24),
    surface = RickGreen,
    onSurface = MortyYellow,
    surfaceContainer = Color.White,
    surfaceVariant = SciFiSurfaceVariantLight,
    onSurfaceVariant = Color(0xFF3F4947),
    outline = MultiversePink,
    error = Color(0xFFBA1A1A),
    errorContainer = Color(0xFFFFDAD6),
    onError = Color.White,
    onErrorContainer = Color(0xFF410002)
)

@Composable
fun RickDexTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is set to false by default to showcase the Rick & Morty theme
    dynamicColor: Boolean = false, content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme, typography = Typography, content = content
    )
}
