package com.winteryy.readit.data.remote.search

import androidx.paging.PagingData
import com.winteryy.readit.data.Result
import com.winteryy.readit.model.Book
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    suspend fun searchBooks(
        query: String,
        start: Int = 1,
        display: Int = 20,
        sort: String = "sim"
    ): Result<List<Book>>

    fun searchBooksFlow(
        query: String
    ): Flow<Result<PagingData<Book>>>
}