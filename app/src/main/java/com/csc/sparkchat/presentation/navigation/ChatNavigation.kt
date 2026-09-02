package com.csc.sparkchat.presentation.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.csc.sparkchat.presentation.chat.detail.ChatDetailScreen
import com.csc.sparkchat.presentation.chat.list.ChatListScreen

fun NavGraphBuilder.chatListScreen(navController: NavController) {
    composable<Route.ChatList>(
        enterTransition = { fadeIn(animationSpec = tween(300)) },
        exitTransition = { fadeOut(animationSpec = tween(300)) },
        popEnterTransition = { fadeIn(animationSpec = tween(300)) },
        popExitTransition = { fadeOut(animationSpec = tween(300)) }
    ) {
        ChatListScreen(
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
        ChatDetailScreen(
            onBackClick = {
                navController.popBackStack()
            }
        )
    }
}