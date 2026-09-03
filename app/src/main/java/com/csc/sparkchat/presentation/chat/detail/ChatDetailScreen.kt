package com.csc.sparkchat.presentation.chat.detail

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.paging.compose.LazyPagingItems
import com.csc.sparkchat.core.designsystem.theme.SparkChatTheme
import com.csc.sparkchat.domain.model.Message
import com.csc.sparkchat.domain.model.User
import com.csc.sparkchat.presentation.chat.detail.components.ChatBottomBar
import com.csc.sparkchat.presentation.chat.detail.components.ChatMessageList
import com.csc.sparkchat.presentation.chat.detail.components.ChatMessageListContent
import com.csc.sparkchat.presentation.chat.detail.components.ChatTopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatDetailScreen(
    user: User?,
    lazyPagingItems: LazyPagingItems<Message>?,
    textState: String,
    sendAsOtherUser: Boolean,
    listState: LazyListState,
    onBackClick: () -> Unit,
    onTextChange: (String) -> Unit,
    onSendAsOtherUserChange: (Boolean) -> Unit,
    onSendClick: () -> Unit,
    modifier: Modifier = Modifier,
    messagesList: List<Message>? = null // For preview support
) {
    // Auto-scroll to index 0 (bottom of the chat) whenever the message count updates
    LaunchedEffect(lazyPagingItems?.itemCount ?: messagesList?.size ?: 0) {
        val count = lazyPagingItems?.itemCount ?: messagesList?.size ?: 0
        if (count > 0) {
            listState.animateScrollToItem(0)
        }
    }

    // Show shadow if the list content is taller than the screen viewport
    val showShadow by remember {
        derivedStateOf { listState.canScrollForward || listState.canScrollBackward }
    }

    Scaffold(
        modifier = modifier,
        contentWindowInsets = WindowInsets.safeDrawing,
        topBar = {
            ChatTopBar(
                userName = user?.name,
                avatarId = user?.avatarId,
                sendAsOtherUser = sendAsOtherUser,
                onSendAsOtherUserChange = onSendAsOtherUserChange,
                onBackClick = onBackClick,
                showShadow = showShadow
            )
        },
        bottomBar = {
            ChatBottomBar(
                value = textState,
                onValueChange = onTextChange,
                onSendClick = onSendClick,
                showShadow = showShadow
            )
        }
    ) { padding ->
        if (lazyPagingItems != null) {
            ChatMessageList(
                lazyPagingItems = lazyPagingItems,
                listState = listState,
                currentUserId = 0L,
                modifier = Modifier.padding(padding)
            )
        } else if (messagesList != null) {
            ChatMessageListContent(
                messages = messagesList,
                listState = listState,
                currentUserId = 0L,
                modifier = Modifier.padding(padding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ChatDetailPreview() {
    val sampleMessages = listOf(
        Message(1L, 1L, 0L, "Does 7pm work for you? I've got to go pick up my little brother first from a party", System.currentTimeMillis()),
        Message(2L, 1L, 1L, "Yeh for sure that works. What time do you think?", System.currentTimeMillis() - 60000),
        Message(3L, 1L, 1L, "Wowsa sounds fun", System.currentTimeMillis() - 3600000)
    )

    SparkChatTheme {
        ChatDetailScreen(
            user = User(1L, "Sarah", "avatar_1"),
            lazyPagingItems = null,
            textState = "Hey, Sara looks great",
            sendAsOtherUser = false,
            listState = rememberLazyListState(),
            onBackClick = {},
            onTextChange = {},
            onSendAsOtherUserChange = {},
            onSendClick = {},
            messagesList = sampleMessages
        )
    }
}