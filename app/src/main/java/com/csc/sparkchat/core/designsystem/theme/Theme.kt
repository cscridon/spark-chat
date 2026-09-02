package com.csc.sparkchat.core.designsystem.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color


private val LightColorScheme = lightColorScheme(
    primary = SparkPrimary,
    onPrimary = Color.White,
    secondaryContainer = SparkIncomingBubble,
    onSecondaryContainer = SparkTextMain,
    background = SparkBackground,
    onBackground = SparkTextMain,
    surface = Color.White,
    onSurface = SparkTextMain,
    tertiary = Pink40,
    onTertiary = Color.White
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
