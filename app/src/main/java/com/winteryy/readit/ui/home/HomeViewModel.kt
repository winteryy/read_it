package com.winteryy.readit.ui.home

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.winteryy.readit.R
import com.winteryy.readit.data.Result
import com.winteryy.readit.data.local.bookstorage.BookStorageRepository
import com.winteryy.readit.data.remote.search.SearchRepository
import com.winteryy.readit.model.Book
import com.winteryy.readit.model.Section
import com.winteryy.readit.model.SectionType
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
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
    private val bookStorageRepository: BookStorageRepository,
    @ApplicationContext private val context: Context
): ViewModel() {

    private val _homeUiState = MutableStateFlow<HomeUiState>(
        HomeUiState.FeedState(
            sectionList = emptyList(),
            isLoading = true,
            errorMessage = null
        )
    )
    val homeUiState: StateFlow<HomeUiState> get() = _homeUiState.asStateFlow()

    private val homeFeedSectionList: StateFlow<List<Section>> = combine(
        bookStorageRepository.getReadingBooks(),
        bookStorageRepository.getWishBooks(),
        bookStorageRepository.getRatedBooks()
    ) { readingBooksResult, wishBooksResult, ratedBooksResult ->
        if(readingBooksResult is Result.Error || wishBooksResult is Result.Error || ratedBooksResult is Result.Error) {
            emptyList()
        } else {
            listOf(
                createSectionFromBooksResult(
                    sectionType = SectionType.READING,
                    result = readingBooksResult,
                    emptyMsg = context.getString(R.string.no_registered_book)
                ),
                createSectionFromBooksResult(
                    sectionType = SectionType.WISH,
                    result = wishBooksResult,
                    emptyMsg = context.getString(R.string.no_registered_book)
                ),
                createSectionFromBooksResult(
                    sectionType = SectionType.RATED,
                    result = ratedBooksResult,
                    emptyMsg = context.getString(R.string.no_commented_book))
            )
        }
    }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    private var feedJob: Job? = null

    init {
        setFeedScreen()
    }

    private fun createSectionFromBooksResult(sectionType: SectionType, result: Result<List<Book>>, emptyMsg: String? = null): Section {
        return when(result) {
            is Result.Success -> Section(sectionType, result.data, emptyMsg)
            is Result.Error -> Section(sectionType, emptyList())
        }
    }

    fun setFeedScreen() {
        feedJob?.cancel()

        feedJob = viewModelScope.launch {
            homeFeedSectionList.collectLatest { sectionList ->
                if(sectionList.isNotEmpty()) {
                    _homeUiState.update { HomeUiState.FeedState(
                        sectionList = sectionList,
                        isLoading = false,
                        errorMessage = null
                    ) }
                } else if(!_homeUiState.value.isLoading) {
                    setErrorMessage(context.getString(R.string.error_msg_book_load_fail))
                }
            }
        }
    }

    fun setSearchingScreen() {
        clearFeedScreen()

        if(homeUiState.value !is HomeUiState.SearchResultState) {
            _homeUiState.update {
                HomeUiState.SearchState(
                    isLoading = false,
                    errorMessage = null
                )
            }
        }
    }

    private fun clearFeedScreen() {
        feedJob?.cancel()
        feedJob = null
    }

    fun setSearchResultScreen(query: String) {
        setLoading()
        when(val result = searchRepository.getSearchPagingData(query)) {
            is Result.Error -> {
                setErrorMessage(result.exception.message)
            }
            is Result.Success -> {
                _homeUiState.update {
                    HomeUiState.SearchResultState(
                        result.data,
                        isLoading = false,
                        errorMessage = null
                    )
                }
            }
        }
    }

    fun setSectionDetailScreen(section: Section) {
        setLoading()

        viewModelScope.launch {
            val result = when(section.sectionType) {
                SectionType.RATED -> bookStorageRepository.getRatedBooksPagingFlow()
                SectionType.READING -> bookStorageRepository.getReadingBooksPagingFlow()
                SectionType.WISH -> bookStorageRepository.getWishBooksPagingFlow()
            }

            when(result) {
                is Result.Error -> {
                    setErrorMessage(result.exception.message)
                }
                is Result.Success -> {
                    _homeUiState.update { HomeUiState.SectionDetailState(
                        sectionBookPagingDataFlow = result.data,
                        sectionType = section.sectionType,
                        isLoading = false,
                        errorMessage = null
                    ) }
                }
            }
        }
    }

    private fun setLoading() {
        _homeUiState.value = when(val state = _homeUiState.value) {
            is HomeUiState.FeedState -> state.copy(isLoading = true)
            is HomeUiState.SearchState -> state.copy(isLoading = true)
            is HomeUiState.SearchResultState -> state.copy(isLoading = true)
            is HomeUiState.SectionDetailState -> state.copy(isLoading = true)
        }
    }

    private fun setErrorMessage(msg: String?) {
        _homeUiState.value = when(val state = _homeUiState.value) {
            is HomeUiState.FeedState -> state.copy(isLoading = false, errorMessage = msg)
            is HomeUiState.SearchState -> state.copy(isLoading = false, errorMessage = msg)
            is HomeUiState.SearchResultState -> state.copy(isLoading = false, errorMessage = msg)
            is HomeUiState.SectionDetailState -> state.copy(isLoading = false, errorMessage = msg)
        }
    }

    fun consumeErrorMessage() {
        setErrorMessage(null)
    }

}