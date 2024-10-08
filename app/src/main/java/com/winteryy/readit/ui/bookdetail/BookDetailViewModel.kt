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
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class BookDetailViewModel @Inject constructor(
    private val bookStorageRepository: BookStorageRepository,
    private val commentStorageRepository: CommentStorageRepository
): ViewModel() {
    private val _uiState: MutableStateFlow<BookDetailUiState> = MutableStateFlow(BookDetailUiState.Loading)
    val uiState: StateFlow<BookDetailUiState> get() = _uiState.asStateFlow()

    fun initBook(book: Book?) {
        book?.let {
            viewModelScope.launch {
                //todo data consistency 문제 있음, map merge 등으로 변경하기
                launch {
                    bookStorageRepository.getBookFlowByIsbn(book.isbn).collectLatest { result ->
                        when(result) {
                            is Result.Error -> {
                                if(result.exception is LocalError.NoMatchItemError) {
                                    _uiState.value = BookDetailUiState.Success(
                                        book = it,
                                        hasComment = false
                                    )
                                } else {
                                    _uiState.value = BookDetailUiState.Fail(result.exception.message)
                                }
                            }
                            is Result.Success -> {
                                updateBook(result.data)
                            }
                        }
                    }
                }
                launch {
                    commentStorageRepository.getCommentByIsbn(book.isbn).collectLatest { result ->
                        updateCommentState(result is Result.Success)
                    }
                }
            }
        } ?: { _uiState.value = BookDetailUiState.Fail("책 정보를 정상적으로 불러오지 못 했습니다.") }
    }

    private fun updateBook(loadedBook: Book) {
        _uiState.value = when(val curState = _uiState.value) {
            is BookDetailUiState.Success -> curState.copy(
                book = loadedBook
            )
            else -> BookDetailUiState.Success(
                book = loadedBook,
                hasComment = false
            )
        }
    }

    private fun updateCommentState(hasComment: Boolean) {
        _uiState.value = when(val curState = _uiState.value) {
            is BookDetailUiState.Success -> curState.copy(
                hasComment = hasComment
            )
            else -> curState
        }
    }

    fun toggleWishBook() = viewModelScope.launch {
        val capturedState = _uiState.value

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
        val capturedState = _uiState.value

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
        val capturedState = _uiState.value

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

    fun insertBeforeComment() = viewModelScope.launch {
        val capturedState = _uiState.value

        if(capturedState is BookDetailUiState.Success) {
            bookStorageRepository.setBook(capturedState.book)
        }
    }
}