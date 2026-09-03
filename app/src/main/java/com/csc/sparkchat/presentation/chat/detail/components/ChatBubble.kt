package com.csc.sparkchat.presentation.chat.detail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DoneAll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.csc.sparkchat.R
import com.csc.sparkchat.core.designsystem.theme.SparkChatTheme

@Composable
fun ChatBubble(
    message: String,
    isSentByMe: Boolean,
    bottomSpacing: Dp
) {
    val bubbleColor = if (isSentByMe) {
        MaterialTheme.colorScheme.primary
    } else {
        MaterialTheme.colorScheme.secondaryContainer
    }
    val textColor = if (isSentByMe) {
        MaterialTheme.colorScheme.onPrimary
    } else {
        MaterialTheme.colorScheme.onSecondaryContainer
    }

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
                style = MaterialTheme.typography.bodyLarge
            )
            if (isSentByMe) {
                Icon(
                    imageVector = Icons.Default.DoneAll,
                    contentDescription = stringResource(R.string.read_status),
                    tint = Color(0xFFFFD700), // Vibrant gold/yellow checkmarks
                    modifier = Modifier
                        .size(12.dp)
                        .offset(x = 9.dp, y = 7.dp)
                        .align(Alignment.BottomEnd) // Anchors strictly to bottom-right of the bubble
                )
            }
        }
    }
}

@Preview(showBackground = true, name = "Sent Message")
@Composable
private fun ChatBubbleMePreview() {
    SparkChatTheme {
        ChatBubble(
            message = "Hey, how are you?",
            isSentByMe = true,
            bottomSpacing = 8.dp
        )
    }
}

@Preview(showBackground = true, name = "Received Message")
@Composable
private fun ChatBubbleOtherPreview() {
    SparkChatTheme {
        ChatBubble(
            message = "I'm doing great, thanks!",
            isSentByMe = false,
            bottomSpacing = 8.dp
        )
    }
}
