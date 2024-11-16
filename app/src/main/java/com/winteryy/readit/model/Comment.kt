package com.winteryy.readit.model

import java.util.Date

data class Comment(
    val id: Long,
    val content: String,
    val updateDate: Date,
    val bookIsbn: String,
)
