package com.winteryy.readit.data.local.commentstorage

import androidx.paging.PagingData
import com.winteryy.readit.data.Result
import com.winteryy.readit.model.Book
import com.winteryy.readit.model.Comment
import kotlinx.coroutines.flow.Flow

interface CommentStorageRepository {

    suspend fun insertComment(comment: Comment): Result<Unit>
    fun getRecentComments(): Flow<Result<List<Pair<Comment, Book>>>>
    fun getAllCommentsPagingFlow(): Result<Flow<PagingData<Comment>>>
    fun getCommentsWithBooks(): Flow<Result<List<Pair<Comment, Book>>>>
    fun getCommentsWithBooksPagingFlow(): Result<Flow<PagingData<Pair<Comment, Book>>>>
    fun getCommentNum(): Flow<Result<Int>>
    fun getCommentByIsbn(isbn: String): Flow<Result<Comment>>
}