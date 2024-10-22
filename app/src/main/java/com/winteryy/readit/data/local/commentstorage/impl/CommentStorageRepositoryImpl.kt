package com.winteryy.readit.data.local.commentstorage.impl

import android.content.Context
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.winteryy.readit.R
import com.winteryy.readit.data.LocalError
import com.winteryy.readit.data.Result
import com.winteryy.readit.data.local.commentstorage.CommentDao
import com.winteryy.readit.data.local.commentstorage.CommentEntity
import com.winteryy.readit.data.local.commentstorage.CommentStorageRepository
import com.winteryy.readit.data.local.commentstorage.toComment
import com.winteryy.readit.data.local.commentstorage.toCommentBookPair
import com.winteryy.readit.model.Book
import com.winteryy.readit.model.Comment
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CommentStorageRepositoryImpl @Inject constructor(
    private val commentDao: CommentDao,
    @ApplicationContext private val context: Context
): CommentStorageRepository {
    override suspend fun insertComment(comment: Comment): Result<Unit> {
        try {
            commentDao.insertComment(
                CommentEntity(
                    id = comment.id,
                    content = comment.content,
                    updateDate = comment.updateDate,
                    bookIsbn = comment.bookIsbn
                )
            )
            return Result.Success(Unit)
        } catch (e: Exception) {
            return Result.Error(
                LocalError.LocalDbError(context.getString(R.string.db_error_insertion_fail))
            )
        }
    }

    override fun getRecentComments(): Flow<Result<List<Pair<Comment, Book>>>> {
        return commentDao.getRecentComments()
            .map { dtoList ->
                try {
                    Result.Success(dtoList.map { it.toCommentBookPair() })
                } catch (e: Exception) {
                    Result.Error(
                        LocalError.LocalDbError(context.getString(R.string.db_error_load_fail))
                    )
                }
            }
    }

    override fun getAllCommentsPagingFlow(): Result<Flow<PagingData<Comment>>> {
        return try {
            Result.Success(
                Pager(
                    config = PagingConfig(
                        pageSize = PAGE_SIZE,
                        enablePlaceholders = false,
                        maxSize = PAGE_SIZE * 3
                    ),
                    pagingSourceFactory = { commentDao.getAllCommentsPaging() }
                ).flow
                    .map { pagingData ->
                        pagingData.map { it.toComment() }
                    }
            )
        } catch (e: Exception) {
            Result.Error(
                LocalError.LocalDbError(context.getString(R.string.db_error_load_fail))
            )
        }
    }

    override fun getCommentsWithBooks(): Flow<Result<List<Pair<Comment, Book>>>> {
        return commentDao.getCommentsWithBooks()
            .map { dtoList ->
                try {
                    Result.Success(dtoList.map { it.toCommentBookPair() } )
                } catch (e: Exception) {
                    Result.Error(
                        LocalError.LocalDbError(context.getString(R.string.db_error_load_fail))
                    )
                }
            }
    }

    override fun getCommentsWithBooksPagingFlow(): Result<Flow<PagingData<Pair<Comment, Book>>>> {
        return try {
            Result.Success(
                Pager(
                    config = PagingConfig(
                        pageSize = PAGE_SIZE,
                        enablePlaceholders = false,
                        maxSize = PAGE_SIZE * 3
                    ),
                    pagingSourceFactory = { commentDao.getCommentsWithBooksPaging() }
                ).flow
                    .map { pagingData ->
                        pagingData.map { it.toCommentBookPair() }
                    }
            )
        } catch (e: Exception) {
            Result.Error(
                LocalError.LocalDbError(context.getString(R.string.db_error_load_fail))
            )
        }
    }

    override fun getCommentNum(): Flow<Result<Int>> {
        return commentDao.getCommentNumFlow()
            .map { num ->
                try {
                    Result.Success(num)
                } catch (e: Exception) {
                    Result.Error(
                        LocalError.LocalDbError(context.getString(R.string.db_error_load_fail))
                    )
                }
            }
    }

    override fun getCommentByIsbn(isbn: String): Flow<Result<Comment>> {
        return commentDao.getCommentByIsbn(isbn = isbn)
            .map { commentEntity ->
                try {
                    if (commentEntity != null) {
                        Result.Success(commentEntity.toComment())
                    } else {
                        Result.Error(
                            LocalError.NoMatchItemError
                        )
                    }
                } catch (e: Exception) {
                    Result.Error(
                        LocalError.LocalDbError(context.getString(R.string.db_error_load_fail))
                    )
                }
            }
    }

    override suspend fun deleteCommentById(id: Long): Result<Unit> {
        try {
            commentDao.deleteCommentById(id)
            return Result.Success(Unit)
        } catch (e: Exception) {
            return Result.Error(
                LocalError.LocalDbError(context.getString(R.string.db_error_deletion_fail))
            )
        }
    }

    companion object {
        private const val PAGE_SIZE = 20
    }
}
