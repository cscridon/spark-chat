package com.csc.sparkchat.presentation.chat.list.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.csc.sparkchat.domain.usecase.GetChatSummariesUseCase
import com.csc.sparkchat.presentation.chat.list.state.ChatListUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class ChatListViewModel @Inject constructor(
    getChatSummariesUseCase: GetChatSummariesUseCase
) : ViewModel() {

    val uiState: StateFlow<ChatListUiState> = getChatSummariesUseCase()
        .map { ChatListUiState.Success(it) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = ChatListUiState.Loading
        )
}