package com.csc.sparkchat.presentation.chat.detail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun ChatBubble(
    message: String,
    isSentByMe: Boolean,
    bottomSpacing: Dp
) {
    val bubbleColor = if (isSentByMe) Color(0xFFFE0979) else Color(0xFFEFEFEF)
    val textColor = if (isSentByMe) Color.White else Color(0xFF1F1F1F)

    // Asymmetrical rounded corners
    val shape = RoundedCornerShape(
        topStart = 18.dp,
        topEnd = 18.dp,
        bottomStart = if (isSentByMe) 18.dp else 4.dp,
        bottomEnd = if (isSentByMe) 4.dp else 18.dp
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = bottomSpacing),
        horizontalAlignment = if (isSentByMe) Alignment.End else Alignment.Start
    ) {
        Box(
            modifier = Modifier
                .widthIn(max = 290.dp)
                .background(color = bubbleColor, shape = shape)
                .padding(horizontal = 14.dp, vertical = 10.dp)
        ) {
            Text(
                text = message,
                color = textColor,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}