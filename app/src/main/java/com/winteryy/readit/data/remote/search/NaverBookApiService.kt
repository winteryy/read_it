package com.winteryy.readit.data.remote.search

import retrofit2.http.GET
import retrofit2.http.Query

interface NaverBookApiService {

    @GET("v1/search/book.json")
    suspend fun searchBooks(
        @Query("query") query: String,
        @Query("start") start: Int,
        @Query("display") display: Int,
        @Query("sort") sort: String
    ): SearchResponse

}