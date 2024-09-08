package com.winteryy.readit.data.local.bookstorage

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.winteryy.readit.model.Book
import com.winteryy.readit.model.BookSaveStatus
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
    val savedDate: Date,
    val bookSaveStatus: BookSaveStatus,
    val rating: Float,
    val ratedDate: Date,
)

fun BookEntity.toBook() = Book(
    title = title,
    image = image,
    author = author,
    publisher = publisher,
    isbn = isbn,
    description = description,
    pubDate = pubDate,
    saveDate = savedDate,
    bookSaveStatus = bookSaveStatus,
    rating = rating,
    ratedDate = ratedDate,
)
