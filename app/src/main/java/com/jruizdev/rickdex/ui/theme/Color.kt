package com.jruizdev.rickdex.ui.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

// Rick & Morty Color Palette
val RickGreen = Color(0xFF39a935)
val PortalGreen = Color(0xFF97CE4C)
val PortalGreenBright = Color(0xFFC7FF6B)
val PortalGreenDark = Color(0xFF3D6C00)

val RickCyan = Color(0xFF00B5CC)
val RickCyanLight = Color(0xFF78ECFF)
val RickCyanDark = Color(0xFF006875)

val MortyYellow = Color(0xFFF0E14A)
val MortyYellowDark = Color(0xFF6A5F00)

val textYellow = Color(0xFFEAD900)
val yellowToolbar = Color(0xFFDDDB00)

val MultiversePink = Color(0xFFE89AC7)
val DimensionPurple = Color(0xFF8B5CF6)

// Dark Theme (Space & Citadel sci-fi aesthetic)
val SpaceSurfaceDark = Color(0xFF141A24)
val SpaceSurfaceVariantDark = Color(0xFF1F2836)
val SpaceTextPrimaryDark = Color(0xFFE2E8F0)
val SpaceTextSecondaryDark = Color(0xFF94A3B8)

val backgroundWine = Color(0xFF270d18)

val pinkVibrantAccent = Color(0xFFe5007e)

// Light Theme
val SciFiBackgroundLight = Color(0xFFF3F3F3)
val SciFiSurfaceLight = Color(0xFFFFFFFF)
val SciFiSurfaceVariantLight = Color(0xFFE0E9E5)

val portalColorBrush =
    Brush.horizontalGradient(
        listOf(
            PortalGreenBright,
            PortalGreen,
            PortalGreenDark, PortalGreenBright,
            PortalGreen,
            PortalGreenDark, PortalGreenBright,
            PortalGreen,
            PortalGreenDark,
        )
    )
