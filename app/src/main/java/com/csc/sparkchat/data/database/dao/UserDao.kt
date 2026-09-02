package com.csc.sparkchat.data.database.dao

import androidx.room3.Dao
import androidx.room3.Query
import com.csc.sparkchat.data.database.dto.UserWithLastMessageDto
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("""
        SELECT 
            u.id AS id, 
            u.name AS name, 
            u.avatar_id AS avatar_id,
            m.content AS last_message,
            m.timestamp AS last_message_timestamp
        FROM users u
        LEFT JOIN messages m ON m.id = (
            SELECT id FROM messages 
            WHERE user_id = u.id 
            ORDER BY timestamp DESC 
            LIMIT 1
        )
        ORDER BY m.timestamp DESC
    """)
    fun getUsersWithLastMessage(): Flow<List<UserWithLastMessageDto>>
}