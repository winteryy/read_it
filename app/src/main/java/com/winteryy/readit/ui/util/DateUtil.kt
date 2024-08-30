package com.winteryy.readit.ui.util

import java.util.Calendar
import java.util.Date

fun Date.getYearByCalender(): Int {
    return Calendar.getInstance().apply {
        time = this.time
    }.get(Calendar.YEAR)
}