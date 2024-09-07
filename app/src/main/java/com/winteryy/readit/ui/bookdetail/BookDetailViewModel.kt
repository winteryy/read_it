package com.winteryy.readit.ui.bookdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.winteryy.readit.data.LocalError
import com.winteryy.readit.data.Result
import com.winteryy.readit.data.local.bookstorage.BookStorageRepository
import com.winteryy.readit.model.Book
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
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
                val localQueryResult = bookStorageRepository.getBookByIsbn(it.isbn)

                when(localQueryResult) {
                    is Result.Error -> {
                        if(localQueryResult.exception is LocalError.NoMatchItemError) {
                            _bookState.value = BookDetailUiState.Success(it)
                        } else {
                            _bookState.value = BookDetailUiState.Fail(localQueryResult.exception.message)
                        }
                    }
                    is Result.Success -> _bookState.value = BookDetailUiState.Success(localQueryResult.data)
                }
            }
        } ?: { _bookState.value = BookDetailUiState.Fail("책 정보를 정상적으로 불러오지 못 했습니다.") }

    }
}