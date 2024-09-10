package com.winteryy.readit.ui.home

import androidx.paging.PagingData
import com.winteryy.readit.model.Book
import com.winteryy.readit.model.Section
import kotlinx.coroutines.flow.Flow

sealed interface HomeUiState {

    data class FeedState(
        val sectionList: List<Section>
    ): HomeUiState

    data class SearchState(
        val logList: List<String> = emptyList()
    ): HomeUiState

    data class SearchResultState(
        val query: String,
        val bookPagingDataFlow: Flow<PagingData<Book>>
    ): HomeUiState

    data class SectionDetailState(
        val section: Section
    ): HomeUiState
}