package com.winteryy.readit.data.local.commentstorage.impl

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.winteryy.readit.data.LocalError
import com.winteryy.readit.data.local.commentstorage.CommentDao
import com.winteryy.readit.data.local.commentstorage.CommentEntity
import com.winteryy.readit.data.local.commentstorage.CommentStorageRepository
import com.winteryy.readit.model.Book
import com.winteryy.readit.model.Comment
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import com.winteryy.readit.data.Result
import com.winteryy.readit.data.local.bookstorage.toBook
import com.winteryy.readit.data.local.commentstorage.toComment
import com.winteryy.readit.data.local.commentstorage.toCommentBookPair
import kotlinx.coroutines.flow.map

class CommentStorageRepositoryImpl @Inject constructor(
    private val commentDao: CommentDao
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
                LocalError.LocalDbError(e.message)
            )
        }
    }

    override fun getRecentComments(): Flow<Result<List<Comment>>> {
        return commentDao.getRecentComments()
            .map { entityList ->
                try {
                    Result.Success(entityList.map { it.toComment() })
                } catch (e: Exception) {
                    Result.Error(
                        LocalError.LocalDbError(e.message)
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
                LocalError.LocalDbError(e.message)
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
                        LocalError.LocalDbError(e.message)
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
                LocalError.LocalDbError(e.message)
            )
        }
    }

    companion object {
        private const val PAGE_SIZE = 20
    }
}
