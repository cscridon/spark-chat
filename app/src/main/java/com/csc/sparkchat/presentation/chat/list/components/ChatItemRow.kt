package com.csc.sparkchat.presentation.chat.list.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.csc.sparkchat.R
import com.csc.sparkchat.core.designsystem.theme.SparkChatTheme
import com.csc.sparkchat.domain.model.ChatSummary
import com.csc.sparkchat.domain.model.User

@Composable
fun ChatItemRow(
    summary: ChatSummary,
    onClick: () -> Unit
) {
    val isNewChat = summary.lastMessage.isBlank()
    val snippetText = if (isNewChat) {
        stringResource(R.string.chat_list_empty_snippet)
    } else {
        summary.lastMessage
    }
    val fontStyle = if (isNewChat) FontStyle.Italic else FontStyle.Normal
    val snippetColor = if (isNewChat) {
        MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f) // Disabled
    } else {
        MaterialTheme.colorScheme.onSurfaceVariant // Standard preview opacity
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = "file:///android_asset/${summary.user.avatarId}",
            contentDescription = null,
            modifier = Modifier
                .size(48.dp)
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.primary,
                    shape = CircleShape
                )
                .clip(CircleShape),
            error = if (LocalInspectionMode.current) {
                painterResource(R.drawable.img_avatar_mock)
            } else {
                ColorPainter(MaterialTheme.colorScheme.surface)
            }
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = summary.user.name,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
            Text(
                text = snippetText,
                style = MaterialTheme.typography.bodyLarge.copy(fontStyle = fontStyle),
                color = snippetColor,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Preview(showBackground = true, name = "Normal Chat")
@Composable
private fun ChatItemRowNormalPreview() {
    SparkChatTheme {
        ChatItemRow(
            summary = ChatSummary(
                user = User(1L, "Sarah", "avatar_1"),
                lastMessage = "Hey, looks great!",
                timestamp = System.currentTimeMillis()
            ),
            onClick = {}
        )
    }
}

@Preview(showBackground = true, name = "Empty Chat (New)")
@Composable
private fun ChatItemRowEmptyPreview() {
    SparkChatTheme {
        ChatItemRow(
            summary = ChatSummary(
                user = User(2L, "John Doe", "avatar_2"),
                lastMessage = "",
                timestamp = System.currentTimeMillis()
            ),
            onClick = {}
        )
    }
}