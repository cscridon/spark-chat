package com.csc.sparkchat.presentation.navigation

import kotlinx.serialization.Serializable

sealed interface Route {

    @Serializable
    data object ChatList : Route

    @Serializable
    data class ChatDetail(val userId: Long) : Route
}