package com.csc.sparkchat.di

import com.csc.sparkchat.data.datasource.ChatLocalDataSource
import com.csc.sparkchat.data.datasource.ChatLocalDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class LocalDataSourceModule {

    @Binds
    @Singleton
    abstract fun bindChatLocalDataSource(
        impl: ChatLocalDataSourceImpl
    ): ChatLocalDataSource
}