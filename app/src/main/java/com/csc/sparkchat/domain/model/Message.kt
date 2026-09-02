package com.csc.sparkchat.domain.model

data class Message(
    val id: Long,
    val userId: Long,
    val senderId: Long,
    val content: String,
    val timestamp: Long
)