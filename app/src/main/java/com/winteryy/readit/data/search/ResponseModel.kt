package com.winteryy.readit.data.search

import com.google.gson.annotations.SerializedName
import java.util.Date

data class SearchResponse(
    @SerializedName("lastBuildDate")
    val lastBuildDate: Date,
    @SerializedName("total")
    val total: Int,
    @SerializedName("start")
    val start: Int,
    @SerializedName("display")
    val display: Int,
    @SerializedName("items")
    val items: List<BookItemResponse>
)

data class BookItemResponse(
    @SerializedName("title")
    val title: String,
    @SerializedName("link")
    val link: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("author")
    val author: String,
    @SerializedName("discount")
    val discount: Int?,
    @SerializedName("publisher")
    val publisher: String,
    @SerializedName("isbn")
    val isbn: Int,
    @SerializedName("description")
    val description: String,
    @SerializedName("pubdate")
    val pubDate: Date,
)
