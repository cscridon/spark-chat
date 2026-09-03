package com.csc.sparkchat.presentation.chat.detail.viewmodel

import androidx.lifecycle.SavedStateHandle
import com.csc.sparkchat.domain.model.User
import com.csc.sparkchat.domain.usecase.GetMessagesPagedUseCase
import com.csc.sparkchat.domain.usecase.GetUserByIdUseCase
import com.csc.sparkchat.domain.usecase.SendMessageUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [35])
class ChatDetailViewModelTest {

    private val getMessagesPagedUseCase: GetMessagesPagedUseCase = mockk()
    private val getUserByIdUseCase: GetUserByIdUseCase = mockk()
    private val sendMessageUseCase: SendMessageUseCase = mockk()
    
    private val testDispatcher = StandardTestDispatcher()
    private val userId = 1L

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        every { getMessagesPagedUseCase(userId) } returns emptyFlow()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when initialized, loads user data`() = runTest {
        val user = User(userId, "Sarah", "avatar_1")
        coEvery { getUserByIdUseCase(userId) } returns user
        
        val savedStateHandle = SavedStateHandle(mapOf("userId" to userId))
        val viewModel = ChatDetailViewModel(
            savedStateHandle = savedStateHandle,
            getMessagesPagedUseCase = getMessagesPagedUseCase,
            getUserByIdUseCase = getUserByIdUseCase,
            sendMessageUseCase = sendMessageUseCase
        )
        
        advanceUntilIdle()
        
        assertEquals(user, viewModel.user)
    }

    @Test
    fun `onSendMessage as Me, calls use case with senderId 0`() = runTest {
        coEvery { getUserByIdUseCase(userId) } returns mockk()
        coEvery { sendMessageUseCase(any(), any(), any()) } returns Unit
        
        val savedStateHandle = SavedStateHandle(mapOf("userId" to userId))
        val viewModel = ChatDetailViewModel(
            savedStateHandle = savedStateHandle,
            getMessagesPagedUseCase = getMessagesPagedUseCase,
            getUserByIdUseCase = getUserByIdUseCase,
            sendMessageUseCase = sendMessageUseCase
        )
        
        viewModel.onSendMessage("Hello", isOtherUser = false)
        advanceUntilIdle()
        
        coVerify { sendMessageUseCase(userId = userId, senderId = 0L, content = "Hello") }
    }

    @Test
    fun `onSendMessage as Other, calls use case with senderId as userId`() = runTest {
        coEvery { getUserByIdUseCase(userId) } returns mockk()
        coEvery { sendMessageUseCase(any(), any(), any()) } returns Unit
        
        val savedStateHandle = SavedStateHandle(mapOf("userId" to userId))
        val viewModel = ChatDetailViewModel(
            savedStateHandle = savedStateHandle,
            getMessagesPagedUseCase = getMessagesPagedUseCase,
            getUserByIdUseCase = getUserByIdUseCase,
            sendMessageUseCase = sendMessageUseCase
        )
        
        viewModel.onSendMessage("Hi", isOtherUser = true)
        advanceUntilIdle()
        
        coVerify { sendMessageUseCase(userId = userId, senderId = userId, content = "Hi") }
    }
}
