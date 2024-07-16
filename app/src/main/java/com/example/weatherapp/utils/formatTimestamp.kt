package com.example.weatherapp.utils

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

fun formatTimestamp(timestamp: Long): String {
    val instant = Instant.ofEpochMilli(timestamp)
    val formatter = DateTimeFormatter.ofPattern("dd/MM")
        .withZone(ZoneId.systemDefault())

    return formatter.format(instant)
}