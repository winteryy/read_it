package com.winteryy.readit.data.local.bookstorage

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity("books")
data class BookEntity(
    @PrimaryKey
    val isbn: String,
    val title: String,
    val image: String,
    val author: String,
    val publisher: String,
    val description: String,
    val pubDate: Date,
    val bookSaveStatusNum: Int,
    val savedDate: Date,
)
