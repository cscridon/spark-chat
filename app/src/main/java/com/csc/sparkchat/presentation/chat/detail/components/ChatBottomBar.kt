package com.csc.sparkchat.presentation.chat.detail.components

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.csc.sparkchat.R
import com.csc.sparkchat.core.designsystem.components.GradientCircularButton
import com.csc.sparkchat.core.designsystem.theme.SparkChatTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatBottomBar(
    value: String,
    onValueChange: (String) -> Unit,
    onSendClick: () -> Unit,
    modifier: Modifier = Modifier,
    placeholderText: String = stringResource(R.string.type_a_message),
    enabled: Boolean = true,
    showShadow: Boolean = false,
    maxLines: Int = 4,
    gradientColors: List<Color> = listOf(
        MaterialTheme.colorScheme.primary,
        MaterialTheme.colorScheme.tertiary
    )
) {
    val interactionSource = remember { MutableInteractionSource() }

    Surface(
        color = MaterialTheme.colorScheme.surface,
        shadowElevation = if (showShadow) 32.dp else 0.dp,
        modifier = modifier
            .zIndex(1f)
            .drawWithContent {
                if (showShadow) {
                    val shadowHeight = 3.dp.toPx()
                    drawRect(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black.copy(alpha = 0.12f)
                            ),
                            startY = -shadowHeight,
                            endY = 0f
                        ),
                        topLeft = Offset(0f, -shadowHeight),
                        size = Size(size.width, shadowHeight)
                    )
                }
                drawContent()
            }
            .windowInsetsPadding(
                WindowInsets.safeDrawing.only(WindowInsetsSides.Bottom)
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 12.dp),
            verticalAlignment = Alignment.Bottom
        ) {
            BasicTextField(
                value = value,
                onValueChange = onValueChange,
                modifier = Modifier
                    .weight(1f)
                    .defaultMinSize(minHeight = 44.dp),
                textStyle = MaterialTheme.typography.bodyLarge.copy(
                    color = MaterialTheme.colorScheme.onSurface
                ),
                enabled = enabled,
                maxLines = maxLines,
                interactionSource = interactionSource,
                decorationBox = { innerTextField ->
                    OutlinedTextFieldDefaults.DecorationBox(
                        value = value,
                        innerTextField = innerTextField,
                        enabled = enabled,
                        singleLine = false,
                        visualTransformation = VisualTransformation.None,
                        interactionSource = interactionSource,
                        placeholder = {
                            Text(
                                text = placeholderText,
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
                            )
                        },
                        contentPadding = PaddingValues(
                            horizontal = 16.dp,
                            vertical = 9.dp
                        ),
                        container = {
                            OutlinedTextFieldDefaults.Container(
                                enabled = enabled,
                                isError = false,
                                interactionSource = interactionSource,
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                                    unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(
                                        alpha = 0.4f
                                    )
                                ),
                                shape = RoundedCornerShape(22.dp)
                            )
                        }
                    )
                }
            )

            GradientCircularButton(
                onClick = onSendClick,
                icon = Icons.AutoMirrored.Filled.Send,
                contentDescription = stringResource(R.string.send_message),
                modifier = Modifier.padding(start = 8.dp),
                size = 44.dp,
                iconSize = 25.dp,
                iconOffset = 1.dp,
                enabled = enabled && value.isNotBlank(),
                gradientColors = gradientColors
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ChatBottomBarPreview() {
    SparkChatTheme {
        ChatBottomBar(
            value = "",
            onValueChange = {},
            onSendClick = {},
            showShadow = true
        )
    }
}
