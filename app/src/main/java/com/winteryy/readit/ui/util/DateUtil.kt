package com.winteryy.readit.ui.util

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

fun Date.getYearByCalender(): Int {
    return Calendar.getInstance().apply {
        time = this.time
    }.get(Calendar.YEAR)
}

fun Date.toReviewDateText(): String {
    return SimpleDateFormat("yy.MM.dd", Locale.getDefault()).format(this)
}