package com.winteryy.readit.data.local.commentstorage

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.winteryy.readit.data.local.bookstorage.BookEntity
import java.util.Date

@Entity(tableName = "comments")
data class CommentEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val content: String,
    val updateDate: Date,
    val bookIsbn: String,
)

data class CommentWithBookDto(
    val commentEntity: CommentEntity,
    val bookEntity: BookEntity
)