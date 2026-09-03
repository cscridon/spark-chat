package com.csc.sparkchat.presentation.chat.detail.components

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.csc.sparkchat.domain.model.Message
import kotlin.math.abs

private const val ONE_HOUR_MS = 3_600_000L
private const val TWENTY_SECONDS_MS = 20_000L

fun shouldShowDateHeader(current: Message, previous: Message?): Boolean {
    return previous == null || abs(current.timestamp - previous.timestamp) > ONE_HOUR_MS
}

fun calculateMessageBottomSpacing(current: Message, next: Message?): Dp {
    val isNextSameSender = next?.senderId == current.senderId
    val isNextWithin20Sec = next != null && abs(next.timestamp - current.timestamp) < TWENTY_SECONDS_MS
    return if (isNextSameSender && isNextWithin20Sec) 4.dp else 12.dp
}