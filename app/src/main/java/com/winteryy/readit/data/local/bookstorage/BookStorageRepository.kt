package com.winteryy.readit.data.local.bookstorage

import com.winteryy.readit.data.Result
import com.winteryy.readit.model.Book
import kotlinx.coroutines.flow.Flow

interface BookStorageRepository {
    suspend fun setBook(book: Book): Result<Unit>
    fun getWishBooksFlow(): Flow<Result<List<Book>>>
    fun getReadingBooksFlow(): Flow<Result<List<Book>>>
    fun getRatedBooksFlow(): Flow<Result<List<Book>>>
    suspend fun getBookByIsbn(isbn: String): Result<Book>
    fun getBookFlowByIsbn(isbn: String): Flow<Result<Book>>
    suspend fun deleteBookByIsbn(isbn: String): Result<Unit>
}