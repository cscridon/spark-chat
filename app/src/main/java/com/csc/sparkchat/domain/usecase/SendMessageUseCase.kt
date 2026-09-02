package com.csc.sparkchat.domain.usecase

import com.csc.sparkchat.domain.repository.ChatRepository
import javax.inject.Inject

class SendMessageUseCase @Inject constructor(
    private val repository: ChatRepository
) {
    suspend operator fun invoke(userId: Long, senderId: Long, content: String) {
        if (content.isNotBlank()) {
            repository.sendMessage(userId, senderId, content.trim())
        }
    }
}