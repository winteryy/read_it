package com.winteryy.readit.data.search

import retrofit2.http.GET
import retrofit2.http.Query

interface NaverBookApiService {

    @GET("v1/search/book.json")
    suspend fun searchBooks(
        @Query("query") query: String,
        @Query("query") start: Int,
        @Query("query") display: Int,
        @Query("query") sort: String
    ): SearchResponse

}