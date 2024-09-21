package com.winteryy.readit.data.local.commentstorage

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CommentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertComment(commentEntity: CommentEntity)

    @Query("SELECT * FROM comments ORDER BY updateDate DESC LIMIT 5")
    fun getRecentComments(): Flow<List<CommentEntity>>

    @Query("SELECT * FROM comments ORDER BY updateDate DESC")
    fun getAllCommentsPaging(): PagingSource<Int, CommentEntity>

    @Query("SELECT * FROM comments INNER JOIN books ON bookIsbn = books.isbn ORDER BY updateDate DESC LIMIT 20")
    fun getCommentsWithBooks(): Flow<List<CommentWithBookDto>>

    @Query("SELECT * FROM comments INNER JOIN books ON bookIsbn = books.isbn ORDER BY updateDate DESC")
    fun getCommentsWithBooksPaging(): PagingSource<Int, CommentWithBookDto>
}