package com.winteryy.readit.data.local

import androidx.room.TypeConverter
import java.util.Date

class RoomConverters {
    @TypeConverter
    fun fromTimeStamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimeStamp(date: Date?): Long? {
        return date?.time
    }
}