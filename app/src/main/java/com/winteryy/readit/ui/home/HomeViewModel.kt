package com.winteryy.readit.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.winteryy.readit.data.search.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val searchRepository: SearchRepository
): ViewModel() {

//    fun repoTest() {
//        viewModelScope.launch {
//            val result = searchRepository.searchBooks("정의")
//            println(result.toString())
//        }
//    }
}