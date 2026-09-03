package com.csc.sparkchat.presentation.chat.detail.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemKey
import com.csc.sparkchat.core.designsystem.theme.SparkChatTheme
import com.csc.sparkchat.domain.model.Message

@Composable
fun ChatMessageList(
    lazyPagingItems: LazyPagingItems<Message>,
    listState: LazyListState,
    modifier: Modifier = Modifier,
    currentUserId: Long = 0L
) {
    LazyColumn(
        state = listState,
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 12.dp),
        reverseLayout = true
    ) {
        items(
            count = lazyPagingItems.itemCount,
            key = lazyPagingItems.itemKey { it.id }
        ) { index ->
            val current = lazyPagingItems[index] ?: return@items
            val previousMessage =
                if (index < lazyPagingItems.itemCount - 1) lazyPagingItems[index + 1] else null
            val nextMessage = if (index > 0) lazyPagingItems[index - 1] else null

            MessageItem(
                current = current,
                previousMessage = previousMessage,
                nextMessage = nextMessage,
                currentUserId = currentUserId
            )
        }
    }
}

@Composable
fun ChatMessageListContent(
    messages: List<Message>,
    listState: LazyListState,
    modifier: Modifier = Modifier,
    currentUserId: Long = 0L
) {
    LazyColumn(
        state = listState,
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 12.dp),
        reverseLayout = true
    ) {
        itemsIndexed(
            items = messages,
            key = { _, item -> item.id }
        ) { index, current ->
            val previousMessage = if (index < messages.size - 1) messages[index + 1] else null
            val nextMessage = if (index > 0) messages[index - 1] else null

            MessageItem(
                current = current,
                previousMessage = previousMessage,
                nextMessage = nextMessage,
                currentUserId = currentUserId
            )
        }
    }
}

@Composable
private fun MessageItem(
    current: Message,
    previousMessage: Message?,
    nextMessage: Message?,
    currentUserId: Long
) {
    val isMe = current.senderId == currentUserId
    val showHeader = shouldShowDateHeader(current, previousMessage)
    val bottomSpacing = calculateMessageBottomSpacing(current, nextMessage)

    Column {
        if (showHeader) {
            DateSectionHeader(timestamp = current.timestamp)
        }
        ChatBubble(
            message = current.content,
            isSentByMe = isMe,
            bottomSpacing = bottomSpacing
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ChatMessageListPreview() {
    val sampleMessages = listOf(
        Message(1L, 1L, 0L, "Does 7pm work for you? I've got to go pick up my little brother first from a party", System.currentTimeMillis()),
        Message(2L, 1L, 1L, "Yeh for sure that works. What time do you think?", System.currentTimeMillis() - 60000),
        Message(3L, 1L, 1L, "Wowsa sounds fun", System.currentTimeMillis() - 3600000)
    )

    SparkChatTheme {
        ChatMessageListContent(
            messages = sampleMessages,
            listState = rememberLazyListState(),
            currentUserId = 0L
        )
    }
}
