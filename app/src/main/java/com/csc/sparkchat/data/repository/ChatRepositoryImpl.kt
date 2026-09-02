package com.csc.sparkchat.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.csc.sparkchat.data.database.entity.MessageEntity
import com.csc.sparkchat.data.datasource.ChatLocalDataSource
import com.csc.sparkchat.data.mapper.toDomain
import com.csc.sparkchat.domain.model.ChatSummary
import com.csc.sparkchat.domain.model.Message
import com.csc.sparkchat.domain.model.User
import com.csc.sparkchat.domain.repository.ChatRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ChatRepositoryImpl @Inject constructor(
    private val localDataSource: ChatLocalDataSource
) : ChatRepository {

    override fun getChatSummaries(): Flow<List<ChatSummary>> {
        return localDataSource.getUsersWithLastMessage().map { dtos ->
            dtos.map { it.toDomain() }
        }
    }

    override fun getMessagesPaged(userId: Long): Flow<PagingData<Message>> {
        return Pager(
            config = PagingConfig(pageSize = 20, enablePlaceholders = false)
        ) {
            localDataSource.getPagedMessagesByUserId(userId)
        }.flow.map { pagingData ->
            pagingData.map { it.toDomain() }
        }
    }

    override suspend fun sendMessage(userId: Long, senderId: Long, content: String) {
        val entity = MessageEntity(
            userId = userId,
            senderId = senderId,
            content = content,
            timestamp = System.currentTimeMillis()
        )
        localDataSource.insertMessage(entity)
    }

    override suspend fun getUserById(userId: Long): User? {
        return localDataSource.getUserById(userId)?.toDomain()
    }
}