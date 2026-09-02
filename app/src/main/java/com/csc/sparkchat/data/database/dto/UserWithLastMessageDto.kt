package com.csc.sparkchat.data.database.dto

import androidx.room3.ColumnInfo

data class UserWithLastMessageDto(
    @ColumnInfo(name = "id") val userId: Long,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "avatar_id") val avatarId: String,
    @ColumnInfo(name = "last_message") val lastMessage: String?,
    @ColumnInfo(name = "last_message_timestamp") val lastMessageTimestamp: Long?
)