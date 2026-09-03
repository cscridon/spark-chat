package com.csc.sparkchat.presentation.chat.list.viewmodel

import app.cash.turbine.test
import com.csc.sparkchat.domain.model.ChatSummary
import com.csc.sparkchat.domain.model.User
import com.csc.sparkchat.domain.usecase.GetChatSummariesUseCase
import com.csc.sparkchat.presentation.chat.list.state.ChatListUiState
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ChatListViewModelTest {

    private val getChatSummariesUseCase: GetChatSummariesUseCase = mockk()
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initially state is Loading`() = runTest {
        every { getChatSummariesUseCase() } returns flowOf(emptyList())
        val viewModel = ChatListViewModel(getChatSummariesUseCase)
        
        assertEquals(ChatListUiState.Loading, viewModel.uiState.value)
    }

    @Test
    fun `when use case emits data, state becomes Success`() = runTest {
        val summaries = listOf(
            ChatSummary(
                user = User(1L, "Sarah", "avatar_1"),
                lastMessage = "Hey!",
                timestamp = 1000L
            )
        )
        every { getChatSummariesUseCase() } returns flowOf(summaries)
        
        val viewModel = ChatListViewModel(getChatSummariesUseCase)
        
        viewModel.uiState.test {
            // Initial state from StateFlow
            assertEquals(ChatListUiState.Loading, awaitItem())
            // Emitted Success state
            val state = awaitItem()
            assert(state is ChatListUiState.Success)
            assertEquals(summaries, (state as ChatListUiState.Success).chats)
        }
    }
}
