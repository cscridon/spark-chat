package com.csc.sparkchat.domain.model

data class ChatSummary(
    val user: User,
    val lastMessage: String,
    val timestamp: Long
)