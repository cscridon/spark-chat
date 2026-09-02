package com.csc.sparkchat.data.database.dao

import androidx.paging.PagingSource
import androidx.room3.Dao
import androidx.room3.DaoReturnTypeConverters
import androidx.room3.Insert
import androidx.room3.OnConflictStrategy
import androidx.room3.Query
import androidx.room3.paging.PagingSourceDaoReturnTypeConverter
import com.csc.sparkchat.data.database.entity.MessageEntity

@Dao
@DaoReturnTypeConverters(PagingSourceDaoReturnTypeConverter::class)
interface MessageDao {
    @Query("SELECT * FROM messages WHERE user_id = :userId ORDER BY timestamp DESC")
    fun getPagedMessagesByUserId(userId: Long): PagingSource<Int, MessageEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMessage(message: MessageEntity): Long
}