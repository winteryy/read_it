package com.winteryy.readit.ui.bookdetail

import com.winteryy.readit.model.Book

sealed interface BookDetailUiState {
    object Loading: BookDetailUiState
    data class Success(val book: Book, val hasComment: Boolean): BookDetailUiState
    data class Fail(val msg: String): BookDetailUiState
}