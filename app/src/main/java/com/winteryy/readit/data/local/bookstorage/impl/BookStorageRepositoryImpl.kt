package com.winteryy.readit.data.local.bookstorage.impl

import com.winteryy.readit.data.LocalError
import com.winteryy.readit.data.Result
import com.winteryy.readit.data.local.bookstorage.BookDao
import com.winteryy.readit.data.local.bookstorage.BookEntity
import com.winteryy.readit.data.local.bookstorage.BookStorageRepository
import com.winteryy.readit.data.local.bookstorage.toBook
import com.winteryy.readit.model.Book
import com.winteryy.readit.model.BookSaveStatus
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
                    savedDate = book.saveDate?:Date(),
                    bookSaveStatus = BookSaveStatus.NONE,
                    rating = book.rating,
                    ratedDate = book.ratedDate?:Date()
                )
            )
            return Result.Success(Unit)
        } catch (e: Exception) {
            return Result.Error(
                LocalError.LocalDbError(e.message)
            )
        }
    }

    override fun getWishBooksFlow(): Flow<Result<List<Book>>> {
        return bookDao.getWishBooksFlow()
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

    override fun getReadingBooksFlow(): Flow<Result<List<Book>>> {
        return bookDao.getReadingBooksFlow()
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

    override fun getRatedBooksFlow(): Flow<Result<List<Book>>> {
        return bookDao.getRatedBooksFlow()
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

}