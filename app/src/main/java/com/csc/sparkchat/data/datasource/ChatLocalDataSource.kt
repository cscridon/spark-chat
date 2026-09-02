package com.csc.sparkchat.data.datasource

import androidx.paging.PagingSource
import com.csc.sparkchat.data.database.dto.UserWithLastMessageDto
import com.csc.sparkchat.data.database.entity.MessageEntity
import kotlinx.coroutines.flow.Flow

interface ChatLocalDataSource {
    fun getUsersWithLastMessage(): Flow<List<UserWithLastMessageDto>>
    fun getPagedMessagesByUserId(userId: Long): PagingSource<Int, MessageEntity>
    suspend fun insertMessage(entity: MessageEntity)
}