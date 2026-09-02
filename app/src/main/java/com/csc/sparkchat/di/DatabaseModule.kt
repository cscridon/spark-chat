package com.csc.sparkchat.di

import android.content.Context
import androidx.room3.Room
import com.csc.sparkchat.data.database.DbConfig
import com.csc.sparkchat.data.database.SparkChatDatabase
import com.csc.sparkchat.data.database.dao.MessageDao
import com.csc.sparkchat.data.database.dao.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import kotlin.jvm.java

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Provides
    @Singleton
    fun providesGitHubDatabase(@ApplicationContext context: Context): SparkChatDatabase {
        return Room.databaseBuilder(context, SparkChatDatabase::class.java, DbConfig.DB_NAME)
            .createFromAsset(DbConfig.DB_PATH)
            .fallbackToDestructiveMigration() // Handles version mismatches safely
            .build()
    }

    @Provides
    fun provideUserDao(database: SparkChatDatabase): UserDao = database.userDao()

    @Provides
    fun provideMessageDao(database: SparkChatDatabase): MessageDao = database.messageDao()
}