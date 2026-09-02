package com.csc.sparkchat.presentation.chat.list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.csc.sparkchat.presentation.chat.list.components.ChatItemRow
import com.csc.sparkchat.presentation.chat.list.state.ChatListUiState
import com.csc.sparkchat.presentation.chat.list.viewmodel.ChatListViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatListScreen(
    onChatClick: (userId: Long) -> Unit,
    viewModel: ChatListViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = { TopAppBar(title = { Text("SparkChat") }) }
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            when (val current = state) {
                is ChatListUiState.Loading -> CircularProgressIndicator()
                is ChatListUiState.Success -> {
                    LazyColumn {
                        items(current.chats, key = { it.user.id }) { item ->
                            ChatItemRow(
                                summary = item, onClick = { onChatClick(item.user.id) }
                            )
                        }
                    }
                }

                is ChatListUiState.Error -> Text(text = current.message)
            }
        }
    }
}