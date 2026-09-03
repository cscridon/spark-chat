package com.csc.sparkchat.presentation.chat.list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.csc.sparkchat.R
import com.csc.sparkchat.core.designsystem.theme.SparkChatTheme
import com.csc.sparkchat.domain.model.ChatSummary
import com.csc.sparkchat.domain.model.User
import com.csc.sparkchat.presentation.chat.list.components.ChatItemRow
import com.csc.sparkchat.presentation.chat.list.state.ChatListUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatListScreen(
    state: ChatListUiState,
    onChatClick: (userId: Long) -> Unit,
    modifier: Modifier = Modifier
) {
    val gradientColors = listOf(
        MaterialTheme.colorScheme.primary,MaterialTheme.colorScheme.tertiary
    )

    Scaffold(
        modifier = modifier,
        topBar = {
            Surface(
                shadowElevation = 4.dp,
                color = Color.White
            ) {
                TopAppBar(title = {
                    Text(
                        text = stringResource(R.string.topbar_title),
                        style = MaterialTheme.typography.titleLarge.copy(
                            brush = Brush.linearGradient(colors = gradientColors)
                        )
                    )
                })
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.Center
        ) {
            when (state) {
                is ChatListUiState.Loading -> CircularProgressIndicator()
                is ChatListUiState.Success -> {
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        itemsIndexed(
                            items = state.chats,
                            key = { _, item -> item.user.id }
                        ) { index, item ->
                            ChatItemRow(
                                summary = item,
                                onClick = { onChatClick(item.user.id) }
                            )
                            if (index < state.chats.lastIndex) {
                                HorizontalDivider(
                                    modifier = Modifier.padding(horizontal = 16.dp),
                                    thickness = 1.dp,
                                    color = MaterialTheme.colorScheme.secondaryContainer
                                )
                            }
                        }
                    }
                }

                is ChatListUiState.Error -> {
                    Text(
                        text = state.message,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, name = "Loading State")
@Composable
private fun ChatListLoadingPreview() {
    SparkChatTheme {
        ChatListScreen(
            state = ChatListUiState.Loading,
            onChatClick = {}
        )
    }
}

@Preview(showBackground = true, name = "Success State")
@Composable
private fun ChatListSuccessPreview() {
    SparkChatTheme {
        ChatListScreen(
            state = ChatListUiState.Success(
                chats = listOf(
                    ChatSummary(
                        user = User(1L, "Sarah", "avatar_1"),
                        lastMessage = "Hey, looks great!",
                        timestamp = System.currentTimeMillis()
                    ),
                    ChatSummary(
                        user = User(2L, "John", "avatar_2"),
                        lastMessage = "See you later",
                        timestamp = System.currentTimeMillis() - 3600000
                    )
                )
            ),
            onChatClick = {}
        )
    }
}

@Preview(showBackground = true, name = "Error State")
@Composable
private fun ChatListErrorPreview() {
    SparkChatTheme {
        ChatListScreen(
            state = ChatListUiState.Error("Failed to load chats"),
            onChatClick = {}
        )
    }
}
