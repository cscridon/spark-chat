package com.csc.sparkchat.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.csc.sparkchat.presentation.chat.list.ChatListScreen

fun NavGraphBuilder.chatListScreen(navController: NavController) {
    composable<Route.ChatList> {
        ChatListScreen(
            onChatClick = { userId ->
                navController.navigate(Route.ChatDetail(userId))
            }
        )
    }
}

fun NavGraphBuilder.chatDetailScreen(navController: NavController) {
    composable<Route.ChatDetail> {
//        ChatDetailScreen(
//            onBackClick = {
//                navController.popBackStack()
//            }
//        )
    }
}