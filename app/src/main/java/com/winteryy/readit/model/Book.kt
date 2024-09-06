package com.winteryy.readit.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
data class Book(
    val title: String,
    val image: String,
    val author: String,
    val publisher: String,
    val isbn: String,
    val description: String,
    val pubDate: Date,
    val bookSaveStatus: BookSaveStatus = BookSaveStatus.NONE,
    val saveDate: Date? = null,
    val rating: Float = 0.0F
): Parcelable

enum class BookSaveStatus {
    NONE,
    WISH,
    READING,
}
