package com.csc.sparkchat.domain.model

import androidx.annotation.Keep

@Keep
data class ChatSummary(
    val user: User,
    val lastMessage: String,
    val timestamp: Long
)