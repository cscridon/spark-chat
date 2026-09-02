package com.csc.sparkchat.data.database

import androidx.room3.Database
import androidx.room3.RoomDatabase
import com.csc.sparkchat.data.database.dao.MessageDao
import com.csc.sparkchat.data.database.dao.UserDao
import com.csc.sparkchat.data.database.entity.MessageEntity
import com.csc.sparkchat.data.database.entity.UserEntity

@Database(
    entities = [UserEntity::class, MessageEntity::class],
    version = DbConfig.DB_VERSION,
    exportSchema = false
)
abstract class SparkChatDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun messageDao(): MessageDao
}