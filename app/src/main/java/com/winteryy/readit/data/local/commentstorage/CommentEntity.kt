package com.winteryy.readit.data.local.commentstorage

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.winteryy.readit.data.local.bookstorage.BookEntity
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
    val commentEntity: CommentEntity,
    val bookEntity: BookEntity
)