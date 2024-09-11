package com.winteryy.readit.data.local.bookstorage

import androidx.paging.PagingData
import com.winteryy.readit.data.Result
import com.winteryy.readit.model.Book
import kotlinx.coroutines.flow.Flow

interface BookStorageRepository {
    suspend fun setBook(book: Book): Result<Unit>
    fun getWishBooks(): Flow<Result<List<Book>>>
    fun getWishBooksPagingFlow(): Result<Flow<PagingData<Book>>>
    fun getReadingBooks(): Flow<Result<List<Book>>>
    fun getReadingBooksPagingFlow(): Result<Flow<PagingData<Book>>>
    fun getRatedBooks(): Flow<Result<List<Book>>>
    fun getRatedBooksPagingFlow(): Result<Flow<PagingData<Book>>>
    suspend fun getBookByIsbn(isbn: String): Result<Book>
    fun getBookFlowByIsbn(isbn: String): Flow<Result<Book>>
    suspend fun deleteBookByIsbn(isbn: String): Result<Unit>
}