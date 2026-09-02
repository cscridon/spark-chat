package com.csc.sparkchat.data.datasource

import androidx.paging.PagingSource
import com.csc.sparkchat.data.database.dao.MessageDao
import com.csc.sparkchat.data.database.dao.UserDao
import com.csc.sparkchat.data.database.dto.UserWithLastMessageDto
import com.csc.sparkchat.data.database.entity.MessageEntity
import com.csc.sparkchat.data.database.entity.UserEntity
import com.csc.sparkchat.domain.model.User
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ChatLocalDataSourceImpl @Inject constructor(
    private val userDao: UserDao,
    private val messageDao: MessageDao
): ChatLocalDataSource {

    override fun getUsersWithLastMessage(): Flow<List<UserWithLastMessageDto>> {
        return userDao.getUsersWithLastMessage()
    }

    override fun getPagedMessagesByUserId(userId: Long): PagingSource<Int, MessageEntity> {
        return messageDao.getPagedMessagesByUserId(userId)
    }

    override suspend fun insertMessage(entity: MessageEntity) {
        messageDao.insertMessage(entity)
    }

    override suspend fun getUserById(userId: Long): UserEntity? {
        return userDao.getUserById(userId)
    }
}