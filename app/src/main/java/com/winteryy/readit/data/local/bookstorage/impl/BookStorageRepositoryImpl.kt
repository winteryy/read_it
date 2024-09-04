package com.winteryy.readit.data.local.bookstorage.impl

import com.winteryy.readit.data.Result
import com.winteryy.readit.data.local.bookstorage.BookDao
import com.winteryy.readit.data.local.bookstorage.BookEntity
import com.winteryy.readit.data.local.bookstorage.BookStorageRepository
import com.winteryy.readit.data.local.bookstorage.toBook
import com.winteryy.readit.data.toException
import com.winteryy.readit.model.Book
import com.winteryy.readit.model.BookSaveStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.util.Date
import javax.inject.Inject

class BookStorageRepositoryImpl @Inject constructor(
    private val bookDao: BookDao
): BookStorageRepository {

    override suspend fun setWishBook(book: Book): Result<Unit> {
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
                    savedDate = Date(),
                    bookSaveStatus = BookSaveStatus.WISH,
                    rating = book.rating
                )
            )

            return Result.Success(Unit)
        } catch (e: Exception) {
            return Result.Error(e)
        }
    }

    override suspend fun setReadingBook(book: Book): Result<Unit> {
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
                    savedDate = Date(),
                    bookSaveStatus = BookSaveStatus.READING,
                    rating = book.rating
                )
            )

            return Result.Success(Unit)
        } catch (e: Exception) {
            return Result.Error(e)
        }
    }

    override suspend fun rateBook(book: Book, rating: Float): Result<Unit> {
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
                    savedDate = Date(),
                    bookSaveStatus = BookSaveStatus.NONE,
                    rating = rating
                )
            )
            return Result.Success(Unit)
        } catch (e: Exception) {
            return Result.Error(e)
        }
    }

    override fun getWishBooksFlow(): Flow<Result<List<Book>>> {
        return bookDao.getWishBooksFlow().map { entityList ->
            try {
                Result.Success( entityList.map { it.toBook() } )
            } catch (e: Exception) {
                Result.Error(e)
            }
        }.catch { throwable ->
            emit(Result.Error(throwable.toException()))
        }
    }

    override fun getReadingBooksFlow(): Flow<Result<List<Book>>> {
        return bookDao.getReadingBooksFlow().map { entityList ->
            try {
                Result.Success( entityList.map { it.toBook() } )
            } catch (e: Exception) {
                Result.Error(e)
            }
        }.catch { throwable ->
            emit(Result.Error(throwable.toException()))
        }
    }

    override fun getRatedBooksFlow(): Flow<Result<List<Book>>> {
        return bookDao.getRatedBooksFlow().map { entityList ->
            try {
                Result.Success( entityList.map { it.toBook() } )
            } catch (e: Exception) {
                Result.Error(e)
            }
        }.catch { throwable ->
            emit(Result.Error(throwable.toException()))
        }
    }

    override suspend fun deleteBookByIsbn(isbn: String): Result<Unit> {
        return try {
            bookDao.deleteBookByIsbn(isbn)
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

}