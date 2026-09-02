package com.csc.sparkchat.domain.repository

import androidx.paging.PagingData
import com.csc.sparkchat.domain.model.ChatSummary
import com.csc.sparkchat.domain.model.Message
import com.csc.sparkchat.domain.model.User
import kotlinx.coroutines.flow.Flow

interface ChatRepository {
    fun getChatSummaries(): Flow<List<ChatSummary>>
    fun getMessagesPaged(userId: Long): Flow<PagingData<Message>>
    suspend fun sendMessage(userId: Long, senderId: Long, content: String)
    suspend fun getUserById(userId: Long): User?
}