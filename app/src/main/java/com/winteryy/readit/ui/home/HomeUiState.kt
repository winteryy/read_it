package com.winteryy.readit.ui.home

import androidx.paging.PagingData
import com.winteryy.readit.model.Book
import com.winteryy.readit.model.Section
import com.winteryy.readit.model.SectionType
import kotlinx.coroutines.flow.Flow

sealed interface HomeUiState {

    data class FeedState(
        val sectionList: List<Section>
    ): HomeUiState

    data class SearchState(
        val logList: List<String> = emptyList()
    ): HomeUiState

    data class SearchResultState(
        val bookPagingDataFlow: Flow<PagingData<Book>>
    ): HomeUiState

    data class SectionDetailState(
        val sectionBookPagingDataFlow: Flow<PagingData<Book>>,
        val sectionType: SectionType
    ): HomeUiState
}