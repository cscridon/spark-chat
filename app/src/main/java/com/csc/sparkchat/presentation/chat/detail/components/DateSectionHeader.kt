package com.csc.sparkchat.presentation.chat.detail.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.csc.sparkchat.core.common.toSectionHeaderFormat
import com.csc.sparkchat.core.designsystem.theme.SparkChatTheme

@Composable
fun DateSectionHeader(timestamp: Long) {
    val formattedText = rememberSaveable(timestamp) { timestamp.toSectionHeaderFormat() }

    val annotatedText = buildAnnotatedString {
        val parts = formattedText.split(" ", limit = 2)

        if (parts.isNotEmpty()) {
            // Day Name (Bold)
            withStyle(
                style = SpanStyle(
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF8E99A6)
                )
            ) {
                append(parts[0])
            }
        }

        if (parts.size > 1) {
            append(" ") // Visual gap between day and time

            // Time String (Normal)
            withStyle(
                style = SpanStyle(
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFFB0B8C1)
                )
            ) {
                append(parts[1])
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = annotatedText,
            style = MaterialTheme.typography.labelMedium,
            color = Color.Gray
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun DateSectionHeaderPreview() {
    SparkChatTheme {
        DateSectionHeader(timestamp = System.currentTimeMillis())
    }
}
