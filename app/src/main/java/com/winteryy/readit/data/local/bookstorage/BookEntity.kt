package com.winteryy.readit.data.local.bookstorage

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.winteryy.readit.data.util.BookSaveStatusConverter
import com.winteryy.readit.model.Book
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

fun BookEntity.toBook() = Book(
    title = title,
    image = image,
    author = author,
    publisher = publisher,
    isbn = isbn,
    description = description,
    pubDate = pubDate,
    saveDate = savedDate,
    bookSaveStatus = BookSaveStatusConverter.toStatus(bookSaveStatusNum)
)
