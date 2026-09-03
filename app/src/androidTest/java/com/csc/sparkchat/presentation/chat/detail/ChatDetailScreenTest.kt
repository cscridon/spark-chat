package com.csc.sparkchat.presentation.chat.detail

import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.v2.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.csc.sparkchat.core.designsystem.theme.SparkChatTheme
import com.csc.sparkchat.domain.model.Message
import com.csc.sparkchat.domain.model.User
import org.junit.Rule
import org.junit.Test

class ChatDetailScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun messageContent_isDisplayed() {
        val userName = "Sarah"
        val messageText = "Check this out!"
        val messages = listOf(
            Message(1L, 1L, 1L, messageText, System.currentTimeMillis())
        )

        composeTestRule.setContent {
            SparkChatTheme {
                ChatDetailScreen(
                    user = User(1L, userName, "avatar_1"),
                    lazyPagingItems = null,
                    textState = "",
                    sendAsOtherUser = false,
                    listState = rememberLazyListState(),
                    onBackClick = {},
                    onTextChange = {},
                    onSendAsOtherUserChange = {},
                    onSendClick = {},
                    messagesList = messages
                )
            }
        }

        composeTestRule.onNodeWithText(userName).assertIsDisplayed()
        composeTestRule.onNodeWithText(messageText).assertIsDisplayed()
    }
}
