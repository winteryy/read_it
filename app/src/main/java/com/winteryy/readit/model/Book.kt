package com.winteryy.readit.model

import java.util.Date

data class Book(
    val title: String,
    val image: String,
    val author: String,
    val publisher: String,
    val isbn: Int,
    val description: String,
    val pubDate: Date,
)
