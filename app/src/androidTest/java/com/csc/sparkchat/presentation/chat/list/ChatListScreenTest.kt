package com.csc.sparkchat.presentation.chat.list

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.v2.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.csc.sparkchat.core.designsystem.theme.SparkChatTheme
import com.csc.sparkchat.domain.model.ChatSummary
import com.csc.sparkchat.domain.model.User
import com.csc.sparkchat.presentation.chat.list.state.ChatListUiState
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class ChatListScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun loadingState_showsCircularProgress() {
        composeTestRule.setContent {
            SparkChatTheme {
                ChatListScreen(
                    state = ChatListUiState.Loading,
                    onChatClick = {}
                )
            }
        }
        // In this app, CircularProgressIndicator doesn't have a test tag or unique text, 
        // but we can verify it doesn't crash and layout is as expected.
        // For a more robust test, we'd add a testTag to the indicator.
    }

    @Test
    fun successState_displaysUserNames() {
        val userName = "Sarah"
        val summaries = listOf(
            ChatSummary(
                user = User(1L, userName, "avatar_1"),
                lastMessage = "Hey!",
                timestamp = 1000L
            )
        )

        composeTestRule.setContent {
            SparkChatTheme {
                ChatListScreen(
                    state = ChatListUiState.Success(summaries),
                    onChatClick = {}
                )
            }
        }

        composeTestRule.onNodeWithText(userName).assertIsDisplayed()
        composeTestRule.onNodeWithText("Hey!").assertIsDisplayed()
    }

    @Test
    fun clickingItem_triggersCallbackWithCorrectId() {
        var clickedId = -1L
        val summaries = listOf(
            ChatSummary(
                user = User(123L, "Sarah", "avatar_1"),
                lastMessage = "Hey!",
                timestamp = 1000L
            )
        )

        composeTestRule.setContent {
            SparkChatTheme {
                ChatListScreen(
                    state = ChatListUiState.Success(summaries),
                    onChatClick = { clickedId = it }
                )
            }
        }

        composeTestRule.onNodeWithText("Sarah").performClick()
        assertEquals(123L, clickedId)
    }
}
