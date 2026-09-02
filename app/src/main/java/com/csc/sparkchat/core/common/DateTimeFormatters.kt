package com.csc.sparkchat.core.common

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Long.toSectionHeaderFormat(): String {
    val formatter = SimpleDateFormat("EEEE HH:mm", Locale.getDefault())
    return formatter.format(Date(this))
}