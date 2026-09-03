package com.csc.sparkchat.core.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.csc.sparkchat.core.designsystem.theme.SparkChatTheme

@Composable
fun GradientCircularButton(
    onClick: () -> Unit,
    icon: ImageVector,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    size: Dp = 48.dp,
    iconSize: Dp = 24.dp,
    iconOffset: Dp = 0.dp,
    enabled: Boolean = true,
    disabledAlpha: Float = 0.4f,
    gradientColors: List<Color> = listOf(
        MaterialTheme.colorScheme.primary,
        MaterialTheme.colorScheme.tertiary
    )
) {
    Box(
        modifier = modifier
            .size(size)
            .alpha(if (enabled) 1f else disabledAlpha)
            .clip(CircleShape)
            .background(
                brush = Brush.linearGradient(
                    colors = gradientColors
                )
            )
            .clickable(
                enabled = enabled,
                onClick = onClick,
                interactionSource = remember { MutableInteractionSource() },
                indication = ripple(bounded = true)
            ),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            tint = Color.White,
            modifier = Modifier
                .size(iconSize)
                .offset(x = iconOffset)
        )
    }
}

@Preview(showBackground = true, name = "Gradient Circular Button")
@Composable
private fun GradientCircularButtonPreview() {
    SparkChatTheme {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            GradientCircularButton(
                onClick = {},
                icon = Icons.AutoMirrored.Filled.Send,
                contentDescription = "Send"
            )
            GradientCircularButton(
                onClick = {},
                icon = Icons.AutoMirrored.Filled.Send,
                contentDescription = "Send Disabled",
                enabled = false
            )
        }
    }
}