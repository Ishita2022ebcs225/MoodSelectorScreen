package com.example.moodselector.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun formatJournalTime(
    timestamp: String,
    locale: Locale
): String {
    return SimpleDateFormat(
        "dd MMM yyyy, hh:mm a",
        locale
    ).format(Date(timestamp))
}