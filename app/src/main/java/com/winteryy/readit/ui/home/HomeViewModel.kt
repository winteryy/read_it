package com.winteryy.readit.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.winteryy.readit.data.Result
import com.winteryy.readit.data.local.bookstorage.BookStorageRepository
import com.winteryy.readit.data.remote.search.SearchRepository
import com.winteryy.readit.model.Book
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val searchRepository: SearchRepository,
    private val bookStorageRepository: BookStorageRepository
): ViewModel() {

    private val _testBookFlow = MutableStateFlow<Result<List<Book>>>(Result.Success(emptyList()))
    val testBookFlow: StateFlow<Result<List<Book>>> = _testBookFlow.asStateFlow()

    init {
        viewModelScope.launch {
            bookStorageRepository.getWishBooksFlow().collectLatest {
                _testBookFlow.value = it
            }
        }
    }

//    fun repoTest() {
//        viewModelScope.launch {
//            val result = searchRepository.searchBooks("정의")
//            println(result.toString())
//        }
//    }

    fun insertBookTest1() = viewModelScope.launch {
        bookStorageRepository.setWishBook(
            Book(
                "123",
                "123",
                "123",
                "123",
                "123",
                "123",
                Date(),
            )
        )
    }

    fun insertBookTest2() = viewModelScope.launch {
        bookStorageRepository.setWishBook(
            Book(
                "456",
                "456",
                "456",
                "456",
                "456",
                "456",
                Date(),
            )
        )
    }
}