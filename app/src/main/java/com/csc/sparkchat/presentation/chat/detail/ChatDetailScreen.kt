package com.csc.sparkchat.presentation.chat.detail

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import coil3.compose.AsyncImage
import com.csc.sparkchat.presentation.chat.detail.components.ChatBubble
import com.csc.sparkchat.presentation.chat.detail.components.DateSectionHeader
import com.csc.sparkchat.presentation.chat.detail.viewmodel.ChatDetailViewModel
import kotlinx.coroutines.launch
import kotlin.math.abs

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatDetailScreen(
    onBackClick: () -> Unit,
    viewModel: ChatDetailViewModel = hiltViewModel()
) {
    val lazyPagingItems = viewModel.messagesPaged.collectAsLazyPagingItems()
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    var textState by remember { mutableStateOf("") }
    var sendAsOtherUser by remember { mutableStateOf(false) }

    // Auto-scroll to index 0 (bottom of the chat) whenever the message count updates
    LaunchedEffect(lazyPagingItems.itemCount) {
        if (lazyPagingItems.itemCount > 0) {
            listState.animateScrollToItem(0)
        }
    }

    Scaffold(
        contentWindowInsets = WindowInsets.safeDrawing,
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        viewModel.user?.let { user ->
                            AsyncImage(
                                model = "file:///android_asset/${user.avatarId}",
                                contentDescription = null,
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(CircleShape)
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(text = user.name, style = MaterialTheme.typography.titleMedium)
                        }
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    FilterChip(
                        selected = sendAsOtherUser,
                        onClick = { sendAsOtherUser = !sendAsOtherUser },
                        label = { Text(if (sendAsOtherUser) "Replying" else "Me") }
                    )
                }
            )
        },
        bottomBar = {
            Surface(
                tonalElevation = 2.dp,
                // Insets consuming both navigation bars AND soft keyboard (IME)
                modifier = Modifier.windowInsetsPadding(
                    WindowInsets.safeDrawing.only(WindowInsetsSides.Bottom)
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = textState,
                        onValueChange = { textState = it },
                        modifier = Modifier.weight(1f),
                        placeholder = { Text("Type a message...") },
                        maxLines = 4
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    IconButton(
                        onClick = {
                            if (textState.isNotBlank()) {
                                viewModel.onSendMessage(textState, sendAsOtherUser)
                                textState = ""
                                coroutineScope.launch {
                                    listState.animateScrollToItem(0)
                                }
                            }
                        },
                        colors = IconButtonDefaults.iconButtonColors(contentColor = Color(0xFFFE0979))
                    ) {
                        Icon(Icons.AutoMirrored.Filled.Send, contentDescription = "Send")
                    }
                }
            }
        }
    ) { padding ->
        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 12.dp),
            reverseLayout = true
        ) {
            items(
                count = lazyPagingItems.itemCount,
                key = lazyPagingItems.itemKey { it.id }
            ) { index ->
                val current = lazyPagingItems[index] ?: return@items
                val previousMessage = if (index < lazyPagingItems.itemCount - 1) lazyPagingItems[index + 1] else null
                val nextMessage = if (index > 0) lazyPagingItems[index - 1] else null

                val isMe = current.senderId == 0L

                // 1. Calculate section header requirement (> 1 hour gap = 3,600,000 ms or first message)
                val showHeader = previousMessage == null ||
                        abs(current.timestamp - previousMessage.timestamp) > 3_600_000L

                // 2. Calculate sub-20-second spacing rule
                val isNextSameSender = nextMessage?.senderId == current.senderId
                val isNextWithin20Sec = nextMessage != null &&
                        abs(nextMessage.timestamp - current.timestamp) < 20_000L

                val bottomSpacing = if (isNextSameSender && isNextWithin20Sec) 4.dp else 12.dp

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
        }
    }
}