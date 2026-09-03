package com.csc.sparkchat.data.database.dao

import android.content.Context
import androidx.room3.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.csc.sparkchat.data.database.SparkChatDatabase
import com.csc.sparkchat.data.database.entity.MessageEntity
import com.csc.sparkchat.data.database.entity.UserEntity
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class MessageDaoTest {

    private lateinit var userDao: UserDao
    private lateinit var messageDao: MessageDao
    private lateinit var db: SparkChatDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, SparkChatDatabase::class.java).build()
        userDao = db.userDao()
        messageDao = db.messageDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun insertMessage_isSuccessful() = runBlocking {
        // Must insert user first due to ForeignKey constraint
        userDao.insertUsers(listOf(UserEntity(id = 1, name = "Sarah", avatarId = "avatar_1")))

        val message = MessageEntity(id = 1, userId = 1, senderId = 0, content = "Test", timestamp = 100L)
        val id = messageDao.insertMessage(message)
        assertEquals(1L, id)
    }
}
