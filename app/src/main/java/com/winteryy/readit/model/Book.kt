package com.winteryy.readit.model

import java.util.Date

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
    val rating: Double = 0.0
)

enum class BookSaveStatus {
    NONE,
    WISH,
    READING,
}
