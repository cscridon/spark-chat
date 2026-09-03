package com.csc.sparkchat.presentation.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.paging.compose.collectAsLazyPagingItems
import com.csc.sparkchat.presentation.chat.detail.ChatDetailScreen
import com.csc.sparkchat.presentation.chat.detail.viewmodel.ChatDetailViewModel
import com.csc.sparkchat.presentation.chat.list.ChatListScreen
import com.csc.sparkchat.presentation.chat.list.viewmodel.ChatListViewModel
import kotlinx.coroutines.launch

fun NavGraphBuilder.chatListScreen(navController: NavController) {
    composable<Route.ChatList>(
        enterTransition = { fadeIn(animationSpec = tween(300)) },
        exitTransition = { fadeOut(animationSpec = tween(300)) },
        popEnterTransition = { fadeIn(animationSpec = tween(300)) },
        popExitTransition = { fadeOut(animationSpec = tween(300)) }
    ) {
        val viewModel: ChatListViewModel = hiltViewModel()
        val state by viewModel.uiState.collectAsStateWithLifecycle()

        ChatListScreen(
            state = state,
            onChatClick = { userId ->
                navController.navigate(Route.ChatDetail(userId))
            }
        )
    }
}

fun NavGraphBuilder.chatDetailScreen(navController: NavController) {
    composable<Route.ChatDetail>(
        enterTransition = {
            slideInHorizontally(initialOffsetX = { it }, animationSpec = tween(300)) + fadeIn()
        },
        exitTransition = {
            slideOutHorizontally(targetOffsetX = { -it }, animationSpec = tween(300)) + fadeOut()
        },
        popEnterTransition = {
            slideInHorizontally(initialOffsetX = { -it }, animationSpec = tween(300)) + fadeIn()
        },
        popExitTransition = {
            slideOutHorizontally(targetOffsetX = { it }, animationSpec = tween(300)) + fadeOut()
        }
    ) {
        val viewModel: ChatDetailViewModel = hiltViewModel()
        val lazyPagingItems = viewModel.messagesPaged.collectAsLazyPagingItems()
        var textState by rememberSaveable { mutableStateOf("") }
        var sendAsOtherUser by rememberSaveable { mutableStateOf(false) }
        val coroutineScope = rememberCoroutineScope()
        val listState = rememberLazyListState()

        ChatDetailScreen(
            user = viewModel.user,
            lazyPagingItems = lazyPagingItems,
            textState = textState,
            sendAsOtherUser = sendAsOtherUser,
            listState = listState,
            onBackClick = {
                navController.popBackStack()
            },
            onTextChange = { textState = it },
            onSendAsOtherUserChange = { sendAsOtherUser = it },
            onSendClick = {
                if (textState.isNotBlank()) {
                    viewModel.onSendMessage(textState, sendAsOtherUser)
                    textState = ""
                    coroutineScope.launch {
                        listState.animateScrollToItem(0)
                    }
                }
            }
        )
    }
}
