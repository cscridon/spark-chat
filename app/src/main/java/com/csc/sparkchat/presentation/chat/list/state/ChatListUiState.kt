package com.csc.sparkchat.presentation.chat.list.state

import androidx.annotation.Keep
import com.csc.sparkchat.domain.model.ChatSummary
@Keep
sealed interface ChatListUiState {
    object Loading : ChatListUiState
    data class Success(val chats: List<ChatSummary>) : ChatListUiState
    data class Error(val message: String) : ChatListUiState
}