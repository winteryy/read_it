package com.winteryy.readit.data.search.impl

import com.winteryy.readit.data.Result
import com.winteryy.readit.data.search.NaverBookApiService
import com.winteryy.readit.data.search.SearchRepository
import com.winteryy.readit.model.Book
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val naverBookApiService: NaverBookApiService
): SearchRepository {
    override suspend fun searchBooks(
        query: String,
        start: Int,
        display: Int,
        sort: String
    ): Result<List<Book>> {
        return withContext(Dispatchers.IO) {
            try {
                val searchBookList = naverBookApiService.searchBooks(
                    query = query,
                    start = start,
                    display = display,
                    sort = sort
                ).items.map { bookItemResponse ->
                    bookItemResponse.toBook()
                }
                Result.Success(searchBookList)
            } catch (e: Exception) {
                Result.Error(e)
            }
        }
    }
}