package com.winteryy.readit.model

import androidx.annotation.StringRes

data class Section(
    @StringRes val title: Int,
    val bookList: List<Book>
)

