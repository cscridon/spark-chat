package com.csc.sparkchat.data.database.dao

import android.content.Context
import androidx.room3.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.csc.sparkchat.data.database.SparkChatDatabase
import com.csc.sparkchat.data.database.entity.MessageEntity
import com.csc.sparkchat.data.database.entity.UserEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class UserDaoTest {

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
    fun getUsersWithLastMessage_returnsCorrectSummaries() = runBlocking {
        // Arrange
        val user1 = UserEntity(id = 1, name = "Sarah", avatarId = "avatar_1")
        val user2 = UserEntity(id = 2, name = "John", avatarId = "avatar_2")
        userDao.insertUsers(listOf(user1, user2))

        val msg1 = MessageEntity(id = 101, userId = 1, senderId = 1, content = "Msg 1", timestamp = 1000L)
        val msg2 = MessageEntity(id = 102, userId = 1, senderId = 0, content = "Latest Msg User 1", timestamp = 2000L)
        val msg3 = MessageEntity(id = 103, userId = 2, senderId = 2, content = "Latest Msg User 2", timestamp = 1500L)
        
        messageDao.insertMessage(msg1)
        messageDao.insertMessage(msg2)
        messageDao.insertMessage(msg3)

        // Act
        val summaries = userDao.getUsersWithLastMessage().first()

        // Assert
        assertEquals(2, summaries.size)
        
        // Ordered by timestamp desc: User 1 (2000), User 2 (1500)
        assertEquals(1L, summaries[0].userId)
        assertEquals("Latest Msg User 1", summaries[0].lastMessage)
        
        assertEquals(2L, summaries[1].userId)
        assertEquals("Latest Msg User 2", summaries[1].lastMessage)
    }

    @Test
    fun getUserById_returnsCorrectUser() = runBlocking {
        val user = UserEntity(id = 5, name = "Test", avatarId = "av")
        userDao.insertUsers(listOf(user))
        
        val result = userDao.getUserById(5L)
        assertEquals(user, result)
    }
}
