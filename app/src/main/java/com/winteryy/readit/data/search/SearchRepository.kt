package com.winteryy.readit.data.search

import com.winteryy.readit.data.Result
import com.winteryy.readit.model.Book

interface SearchRepository {
    suspend fun searchBooks(query: String, start: Int, display: Int, sort: String): Result<List<Book>>
}