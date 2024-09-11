package com.winteryy.readit.ui.bookdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.winteryy.readit.data.LocalError
import com.winteryy.readit.data.Result
import com.winteryy.readit.data.local.bookstorage.BookStorageRepository
import com.winteryy.readit.model.Book
import com.winteryy.readit.model.BookSaveStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class BookDetailViewModel @Inject constructor(
    private val bookStorageRepository: BookStorageRepository
): ViewModel() {
    private val _bookState: MutableStateFlow<BookDetailUiState> = MutableStateFlow(BookDetailUiState.Loading)
    val bookState: StateFlow<BookDetailUiState> get() = _bookState.asStateFlow()

    fun initBook(book: Book?) {
        book?.let {
            viewModelScope.launch {
                bookStorageRepository.getBookFlowByIsbn(book.isbn).collectLatest { result ->
                    when(result) {
                        is Result.Error -> {
                            if(result.exception is LocalError.NoMatchItemError) {
                                _bookState.value = BookDetailUiState.Success(it)
                            } else {
                                _bookState.value = BookDetailUiState.Fail(result.exception.message)
                            }
                        }
                        is Result.Success -> _bookState.value = BookDetailUiState.Success(result.data)
                    }
                }
            }
        } ?: { _bookState.value = BookDetailUiState.Fail("책 정보를 정상적으로 불러오지 못 했습니다.") }
    }

    fun toggleWishBook() = viewModelScope.launch {
        val capturedState = _bookState.value

        if(capturedState is BookDetailUiState.Success) {
            val needToClear = capturedState.book.bookSaveStatus == BookSaveStatus.WISH
            bookStorageRepository.setBook(
                book = capturedState.book.copy(
                    bookSaveStatus = if(needToClear) BookSaveStatus.NONE else BookSaveStatus.WISH,
                    saveDate = Date()
                )
            )
        }
    }

    fun toggleReadingBook() = viewModelScope.launch {
        val capturedState = _bookState.value

        if(capturedState is BookDetailUiState.Success) {
            val needToClear = capturedState.book.bookSaveStatus == BookSaveStatus.READING
            bookStorageRepository.setBook(
                book = capturedState.book.copy(
                    bookSaveStatus = if(needToClear) BookSaveStatus.NONE else BookSaveStatus.READING,
                    saveDate = Date()
                )
            )
        }
    }

    fun setRating(rating: Float) = viewModelScope.launch {
        val capturedState = _bookState.value

        if(capturedState is BookDetailUiState.Success) {
            bookStorageRepository.setBook(
                book = capturedState.book.copy(
                    bookSaveStatus = BookSaveStatus.NONE,
                    rating = rating,
                    ratedDate = Date()
                )
            )
        }
    }
}