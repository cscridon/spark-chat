package com.csc.sparkchat.presentation.chat.detail.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.csc.sparkchat.domain.model.Message
import com.csc.sparkchat.domain.model.User
import com.csc.sparkchat.domain.usecase.GetMessagesPagedUseCase
import com.csc.sparkchat.domain.usecase.GetUserByIdUseCase
import com.csc.sparkchat.domain.usecase.SendMessageUseCase
import com.csc.sparkchat.presentation.navigation.Route
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    getMessagesPagedUseCase: GetMessagesPagedUseCase,
    getUserByIdUseCase: GetUserByIdUseCase,
    private val sendMessageUseCase: SendMessageUseCase
) : ViewModel() {

    private val navArgs = savedStateHandle.toRoute<Route.ChatDetail>()
    val userId: Long = navArgs.userId

    var user by mutableStateOf<User?>(null)
        private set

    val messagesPaged: Flow<PagingData<Message>> = getMessagesPagedUseCase(userId)
        .cachedIn(viewModelScope)

    init {
        viewModelScope.launch {
            user = getUserByIdUseCase(userId)
        }
    }
    fun onSendMessage(text: String, isOtherUser: Boolean) {
        viewModelScope.launch {
            val senderId = if (isOtherUser) userId else 0L
            sendMessageUseCase(userId = userId, senderId = senderId, content = text)
        }
    }
}