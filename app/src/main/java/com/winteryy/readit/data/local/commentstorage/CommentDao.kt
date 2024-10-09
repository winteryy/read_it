package com.winteryy.readit.data.local.commentstorage

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface CommentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertComment(commentEntity: CommentEntity)

    @Query("SELECT * FROM comments INNER JOIN books ON bookIsbn = books.isbn ORDER BY updateDate DESC LIMIT 5")
    fun getRecentComments(): Flow<List<CommentWithBookDto>>

    @Query("SELECT * FROM comments ORDER BY updateDate DESC")
    fun getAllCommentsPaging(): PagingSource<Int, CommentEntity>

    @Transaction
    @Query("SELECT * FROM comments INNER JOIN books ON bookIsbn = books.isbn ORDER BY updateDate DESC LIMIT 20")
    fun getCommentsWithBooks(): Flow<List<CommentWithBookDto>>

    @Transaction
    @Query("SELECT * FROM comments INNER JOIN books ON bookIsbn = books.isbn ORDER BY updateDate DESC")
    fun getCommentsWithBooksPaging(): PagingSource<Int, CommentWithBookDto>

    @Query("SELECT COUNT(*) FROM comments")
    fun getCommentNumFlow(): Flow<Int>

    @Query("SELECT * FROM comments WHERE bookIsbn = :isbn")
    fun getCommentByIsbn(isbn: String): Flow<CommentEntity?>

    @Query("DELETE FROM comments WHERE id = :id")
    suspend fun deleteCommentById(id: Long)

}