package com.winteryy.readit.data.local.bookstorage.impl

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.winteryy.readit.data.LocalError
import com.winteryy.readit.data.Result
import com.winteryy.readit.data.local.bookstorage.BookDao
import com.winteryy.readit.data.local.bookstorage.BookEntity
import com.winteryy.readit.data.local.bookstorage.BookStorageRepository
import com.winteryy.readit.data.local.bookstorage.toBook
import com.winteryy.readit.model.Book
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.util.Date
import javax.inject.Inject

class BookStorageRepositoryImpl @Inject constructor(
    private val bookDao: BookDao
) : BookStorageRepository {

    override suspend fun setBook(book: Book): Result<Unit> {
        try {
            bookDao.insertBook(
                BookEntity(
                    isbn = book.isbn,
                    title = book.title,
                    image = book.image,
                    author = book.author,
                    publisher = book.publisher,
                    description = book.description,
                    pubDate = book.pubDate,
                    savedDate = book.saveDate ?: Date(),
                    bookSaveStatus = book.bookSaveStatus,
                    rating = book.rating,
                    ratedDate = book.ratedDate ?: Date()
                )
            )
            return Result.Success(Unit)
        } catch (e: Exception) {
            return Result.Error(
                LocalError.LocalDbError(e.message)
            )
        }
    }

    override fun getWishBooks(): Flow<Result<List<Book>>> {
        return bookDao.getWishBooks()
            .map { entityList ->
                try {
                    Result.Success(entityList.map { it.toBook() })
                } catch (e: Exception) {
                    Result.Error(
                        LocalError.LocalDbError(e.message)
                    )
                }
            }.catch { throwable ->
                emit(
                    Result.Error(
                        LocalError.LocalDbError(throwable.message)
                    )
                )
            }
    }

    override fun getWishBooksPagingFlow(): Result<Flow<PagingData<Book>>> {
        return try {
            Result.Success(
                Pager(
                    config = PagingConfig(
                        pageSize = PAGE_SIZE,
                        enablePlaceholders = false,
                        maxSize = PAGE_SIZE * 3
                    ),
                    pagingSourceFactory = { bookDao.getWishBooksPaging() }
                ).flow
                    .map { pagingData ->
                        pagingData.map { it.toBook() }
                    }
            )
        } catch (e: Exception) {
            Result.Error(
                LocalError.LocalDbError(e.message)
            )
        }
    }

    override fun getReadingBooks(): Flow<Result<List<Book>>> {
        return bookDao.getReadingBooks()
            .map { entityList ->
                try {
                    Result.Success(entityList.map { it.toBook() })
                } catch (e: Exception) {
                    Result.Error(
                        LocalError.LocalDbError(e.message)
                    )
                }
            }
            .catch { throwable ->
                emit(
                    Result.Error(
                        LocalError.LocalDbError(throwable.message)
                    )
                )
            }
    }

    override fun getReadingBooksPagingFlow(): Result<Flow<PagingData<Book>>> {
        return try {
            Result.Success(
                Pager(
                    config = PagingConfig(
                        pageSize = PAGE_SIZE,
                        enablePlaceholders = false,
                        maxSize = PAGE_SIZE * 3
                    ),
                    pagingSourceFactory = { bookDao.getReadingBooksPaging() }
                ).flow
                    .map { pagingData ->
                        pagingData.map { it.toBook() }
                    }
            )
        } catch (e: Exception) {
            Result.Error(
                LocalError.LocalDbError(e.message)
            )
        }
    }

    override fun getRatedBooks(): Flow<Result<List<Book>>> {
        return bookDao.getRatedBooks()
            .map { entityList ->
                try {
                    Result.Success(entityList.map { it.toBook() })
                } catch (e: Exception) {
                    Result.Error(
                        LocalError.LocalDbError(e.message)
                    )
                }
            }.catch { throwable ->
                emit(
                    Result.Error(
                        LocalError.LocalDbError(throwable.message)
                    )
                )
            }
    }

    override fun getRatedBooksPagingFlow(): Result<Flow<PagingData<Book>>> {
        return try {
            Result.Success(
                Pager(
                    config = PagingConfig(
                        pageSize = PAGE_SIZE,
                        enablePlaceholders = false,
                        maxSize = PAGE_SIZE * 3
                    ),
                    pagingSourceFactory = { bookDao.getRatedBooksPaging() }
                ).flow
                    .map { pagingData ->
                        pagingData.map { it.toBook() }
                    }
            )
        } catch (e: Exception) {
            Result.Error(
                LocalError.LocalDbError(e.message)
            )
        }
    }

    override suspend fun getBookByIsbn(isbn: String): Result<Book> {
        return try {
            val result = bookDao.getBookByIsbn(isbn)
            if (result != null) {
                Result.Success(result.toBook())
            } else {
                Result.Error(
                    LocalError.NoMatchItemError
                )
            }
        } catch (e: Exception) {
            Result.Error(
                LocalError.LocalDbError(e.message)
            )
        }
    }

    override fun getBookFlowByIsbn(isbn: String): Flow<Result<Book>> {
        return bookDao.getBookFlowByIsbn(isbn)
            .map { bookEntity ->
                if (bookEntity != null) {
                    Result.Success(bookEntity.toBook())
                } else {
                    Result.Error(
                        LocalError.NoMatchItemError
                    )
                }
            }
            .catch { throwable ->
                emit(
                    Result.Error(
                        LocalError.LocalDbError(throwable.message)
                    )
                )
            }
    }

    override suspend fun deleteBookByIsbn(isbn: String): Result<Unit> {
        return try {
            bookDao.deleteBookByIsbn(isbn)
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(
                LocalError.LocalDbError(e.message)
            )
        }
    }

    override fun getBooksHavingCommentPagingFlow(): Result<Flow<PagingData<Book>>> {
        return try {
            Result.Success(
                Pager(
                    config = PagingConfig(
                        pageSize = PAGE_SIZE,
                        enablePlaceholders = false,
                        maxSize = PAGE_SIZE * 3
                    ),
                    pagingSourceFactory = { bookDao.getBooksHavingCommentPaging() }
                ).flow
                    .map { pagingData ->
                        pagingData.map { it.toBook() }
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