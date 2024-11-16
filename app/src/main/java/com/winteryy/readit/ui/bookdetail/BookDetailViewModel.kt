package com.winteryy.readit.ui.bookdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.winteryy.readit.data.LocalError
import com.winteryy.readit.data.Result
import com.winteryy.readit.data.local.bookstorage.BookStorageRepository
import com.winteryy.readit.data.local.commentstorage.CommentStorageRepository
import com.winteryy.readit.model.Book
import com.winteryy.readit.model.BookSaveStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class BookDetailViewModel @Inject constructor(
    private val bookStorageRepository: BookStorageRepository,
    private val commentStorageRepository: CommentStorageRepository
): ViewModel() {
    private val _uiState: MutableStateFlow<BookDetailUiState> = MutableStateFlow(BookDetailUiState(isLoading = true))
    val uiState: StateFlow<BookDetailUiState> get() = _uiState.asStateFlow()

    fun initBook(book: Book?) {
        book?.let {
            viewModelScope.launch {
                bookStorageRepository.getBookFlowByIsbn(book.isbn).combine(
                    commentStorageRepository.getCommentByIsbn(book.isbn)
                ) { bookResult, commentResult ->
                    when(bookResult) {
                        is Result.Error -> {
                            if(bookResult.exception is LocalError.NoMatchItemError) {
                                BookDetailUiState(
                                    book = it,
                                    hasComment = false
                                )
                            } else {
                                BookDetailUiState(error = BookDetailUiError.InitFailError)
                            }
                        }
                        is Result.Success -> {
                            BookDetailUiState(
                                book = bookResult.data,
                                hasComment = commentResult is Result.Success
                            )
                        }
                    }
                }.collectLatest { state ->
                    _uiState.value = state
                }
            }
        } ?: { _uiState.value = BookDetailUiState(error = BookDetailUiError.InitFailError) }
    }

    fun toggleWishBook() = viewModelScope.launch {
        val needToClear = _uiState.value.book.bookSaveStatus == BookSaveStatus.WISH

        when(val result = bookStorageRepository.setBook(
            book = _uiState.value.book.copy(
                bookSaveStatus = if(needToClear) BookSaveStatus.NONE else BookSaveStatus.WISH,
                saveDate = Date()
            )
        )) {
            is Result.Error -> {
                _uiState.value = BookDetailUiState(error = BookDetailUiError.QueryError(result.exception.message))
            }
            is Result.Success -> { }
        }
    }

    fun toggleReadingBook() = viewModelScope.launch {
        val needToClear = _uiState.value.book.bookSaveStatus == BookSaveStatus.READING

        when(val result = bookStorageRepository.setBook(
            book = _uiState.value.book.copy(
                bookSaveStatus = if(needToClear) BookSaveStatus.NONE else BookSaveStatus.READING,
                saveDate = Date()
            )
        )) {
            is Result.Error -> {
                _uiState.value = BookDetailUiState(error = BookDetailUiError.QueryError(result.exception.message))
            }
            is Result.Success -> { }
        }
    }

    fun setRating(rating: Float) = viewModelScope.launch {
        when(val result = bookStorageRepository.setBook(
            book = _uiState.value.book.copy(
                bookSaveStatus = BookSaveStatus.NONE,
                rating = rating,
                ratedDate = Date()
            )
        )) {
            is Result.Error -> {
                _uiState.value = BookDetailUiState(error = BookDetailUiError.QueryError(result.exception.message))
            }
            is Result.Success -> { }
        }
    }

    fun insertBeforeComment() = viewModelScope.launch {
        bookStorageRepository.setBook(_uiState.value.book)
    }

    fun consumeError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
}