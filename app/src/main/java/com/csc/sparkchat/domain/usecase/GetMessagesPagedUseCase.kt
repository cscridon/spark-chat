package com.csc.sparkchat.domain.usecase

import androidx.paging.PagingData
import com.csc.sparkchat.domain.model.Message
import com.csc.sparkchat.domain.repository.ChatRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMessagesPagedUseCase @Inject constructor(
    private val repository: ChatRepository
) {
    operator fun invoke(userId: Long): Flow<PagingData<Message>> =
        repository.getMessagesPaged(userId)
}