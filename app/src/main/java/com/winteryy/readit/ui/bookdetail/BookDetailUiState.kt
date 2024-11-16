package com.winteryy.readit.ui.bookdetail

import com.winteryy.readit.model.Book

data class BookDetailUiState(
    val isLoading: Boolean = false,
    val error: BookDetailUiError? = null,

    val book: Book = Book.NONE,
    val hasComment: Boolean = false
)

sealed interface BookDetailUiError {
    object InitFailError: BookDetailUiError
    data class QueryError(val msg: String): BookDetailUiError
}