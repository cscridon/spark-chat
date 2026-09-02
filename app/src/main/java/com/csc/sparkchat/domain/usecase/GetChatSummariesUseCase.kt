package com.csc.sparkchat.domain.usecase

import com.csc.sparkchat.domain.model.ChatSummary
import com.csc.sparkchat.domain.repository.ChatRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetChatSummariesUseCase @Inject constructor(
    private val repository: ChatRepository
) {
    operator fun invoke(): Flow<List<ChatSummary>> = repository.getChatSummaries()
}