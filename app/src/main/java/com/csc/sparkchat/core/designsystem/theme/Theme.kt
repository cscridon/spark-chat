package com.csc.sparkchat.core.designsystem.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color


private val LightColorScheme = lightColorScheme(
    // Primary Brands & Containers
    primary = SparkPrimary,
    onPrimary = SparkTextPrimary, // Text inside primary containers (e.g. Sent message text)

    // Secondary Containers (e.g. Incoming Bubbles)
    secondaryContainer = SparkIncomingBubble,
    onSecondaryContainer = SparkTextIncoming, // Text inside incoming bubbles

    // Main App Background
    background = SparkBackground,
    onBackground = SparkTextMain, // Global app text on raw background

    // Surface Elevation (Cards, Top Bars, Bottom Bars)
    surface = Color.White,
    onSurface = SparkTextMain, // High-emphasis body/title text on surfaces
    onSurfaceVariant = SparkTextSecondary, // Low-emphasis text (timestamps, placeholders)

    // Tertiary Accents
    tertiary = SparkTertiary,
    onTertiary = SparkTextTertiary
)

@Composable
fun SparkChatTheme(
    content: @Composable () -> Unit
) {

    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = Typography,
        content = content
    )
}
