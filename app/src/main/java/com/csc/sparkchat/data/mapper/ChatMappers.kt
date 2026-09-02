package com.csc.sparkchat.data.mapper

import com.csc.sparkchat.data.database.dto.UserWithLastMessageDto
import com.csc.sparkchat.data.database.entity.MessageEntity
import com.csc.sparkchat.domain.model.ChatSummary
import com.csc.sparkchat.domain.model.Message
import com.csc.sparkchat.domain.model.User

fun UserWithLastMessageDto.toDomain(): ChatSummary {
    return ChatSummary(
        user = User(
            id = userId,
            name = name,
            avatarId = avatarId
        ),
        lastMessage = lastMessage ?: "",
        timestamp = lastMessageTimestamp ?: 0L
    )
}

fun MessageEntity.toDomain(): Message {
    return Message(
        id = id,
        userId = userId,
        senderId = senderId,
        content = content,
        timestamp = timestamp
    )
}