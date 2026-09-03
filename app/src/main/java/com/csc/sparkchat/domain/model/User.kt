package com.csc.sparkchat.domain.model

import androidx.annotation.Keep

@Keep
data class User(
    val id: Long,
    val name: String,
    val avatarId: String
)