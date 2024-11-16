package com.winteryy.readit.data.local.commentstorage

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.winteryy.readit.model.Book
import com.winteryy.readit.model.BookSaveStatus
import com.winteryy.readit.model.Comment
import java.util.Date

@Entity(tableName = "comments")
data class CommentEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val content: String,
    val updateDate: Date,
    val bookIsbn: String,
)

fun CommentEntity.toComment() = Comment(
    id = id,
    content = content,
    updateDate = updateDate,
    bookIsbn = bookIsbn
)

data class CommentWithBookDto(
    //Comment Column
    val id: Long,
    val content: String,
    val updateDate: Date,
    val bookIsbn: String,
    //Book Column
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

fun CommentWithBookDto.toCommentBookPair() =
    Comment(
        id = id,
        content = content,
        updateDate = updateDate,
        bookIsbn = bookIsbn
    ) to Book(
        title = title,
        image = image,
        author = author,
        publisher = publisher,
        isbn = isbn,
        description = description,
        pubDate = pubDate,
        bookSaveStatus = bookSaveStatus,
        saveDate = savedDate,
        rating = rating,
        ratedDate = ratedDate
    )