package com.winteryy.readit.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.winteryy.readit.data.Result
import com.winteryy.readit.data.local.bookstorage.BookStorageRepository
import com.winteryy.readit.data.remote.search.SearchRepository
import com.winteryy.readit.model.Book
import com.winteryy.readit.model.Section
import com.winteryy.readit.model.SectionType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val searchRepository: SearchRepository,
    private val bookStorageRepository: BookStorageRepository
): ViewModel() {

    private val _homeUiState = MutableStateFlow<HomeUiState>(HomeUiState.FeedState(emptyList()))
    val homeUiState: StateFlow<HomeUiState> get() = _homeUiState.asStateFlow()

    private val homeFeedSectionList: StateFlow<List<Section>> = combine(
        bookStorageRepository.getReadingBooksFlow(),
        bookStorageRepository.getWishBooksFlow(),
        bookStorageRepository.getRatedBooksFlow()
    ) { readingBooksResult, wishBooksResult, ratedBooksResult ->

        listOf(
            createSectionFromBooksResult(SectionType.READING, readingBooksResult),
            createSectionFromBooksResult(SectionType.WISH, wishBooksResult),
            createSectionFromBooksResult(SectionType.RATED, ratedBooksResult)

        )
    }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    private var feedJob: Job? = null
    private var searchJob: Job? = null

    init {
        setFeedScreen()
    }

    private fun createSectionFromBooksResult(sectionType: SectionType, result: Result<List<Book>>): Section {
        return when(result) {
            is Result.Success -> Section(sectionType, result.data)
            is Result.Error -> Section(sectionType, emptyList())
        }
    }

    fun setFeedScreen() {
        feedJob?.cancel()

        feedJob = viewModelScope.launch {
            homeFeedSectionList.collectLatest { sectionList ->
                _homeUiState.update { HomeUiState.FeedState(sectionList) }
            }
        }
    }

    fun setSearchingScreen() {
        clearFeedScreen()

        if(homeUiState.value !is HomeUiState.SearchResultState) {
            _homeUiState.update {
                HomeUiState.SearchState()
            }
        }
    }

    private fun clearFeedScreen() {
        feedJob?.cancel()
        feedJob = null
    }

//    fun setSearchResultScreen(query: String) = viewModelScope.launch {
//        when(val result = searchRepository.searchBooks(query)) {
//            is Result.Error -> {
//                //todo 에러 핸들링
//            }
//            is Result.Success -> {
//                _homeUiState.update {
//                    HomeUiState.SearchResultState(
//                        query,
//                        result.data
//                    )
//                }
//            }
//        }
//    }

    fun setSearchResultScreen(query: String) {
        when(val result = searchRepository.getSearchPagingData(query)) {
            is Result.Error -> {
                //todo 에러 핸들링
            }
            is Result.Success -> {
                _homeUiState.update {
                    HomeUiState.SearchResultState(
                        query,
                        result.data
                    )
                }
            }
        }
    }

    private fun clearSearchScreen() {
        searchJob?.cancel()
        searchJob = null
    }

    fun setSectionDetailScreen(section: Section) {
        _homeUiState.update {
            HomeUiState.SectionDetailState(section)
        }
    }

}