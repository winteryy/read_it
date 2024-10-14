package com.winteryy.readit.ui.home

import androidx.paging.PagingData
import com.winteryy.readit.model.Book
import com.winteryy.readit.model.Section
import com.winteryy.readit.model.SectionType
import kotlinx.coroutines.flow.Flow

sealed interface HomeUiState {
    val isLoading: Boolean
    val errorMessage: String?

    data class FeedState(
        val sectionList: List<Section>,
        override val isLoading: Boolean,
        override val errorMessage: String?
    ): HomeUiState

    data class SearchState(
        val logList: List<String> = emptyList(),
        override val isLoading: Boolean,
        override val errorMessage: String?
    ): HomeUiState

    data class SearchResultState(
        val bookPagingDataFlow: Flow<PagingData<Book>>,
        override val isLoading: Boolean,
        override val errorMessage: String?
    ): HomeUiState

    data class SectionDetailState(
        val sectionBookPagingDataFlow: Flow<PagingData<Book>>,
        val sectionType: SectionType,
        override val isLoading: Boolean,
        override val errorMessage: String?
    ): HomeUiState
}