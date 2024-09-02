package com.winteryy.readit.ui.home

import com.winteryy.readit.model.Book
import com.winteryy.readit.model.Section

sealed interface HomeUiState {

    data class FeedState(
        val sectionList: List<Section>
    ): HomeUiState

    data class SearchState(
        val logList: List<String> = emptyList()
    ): HomeUiState

    data class SearchResultState(
        val query: String,
        val bookList: List<Book>
    ): HomeUiState

    data class SectionDetailState(
        val section: Section
    ): HomeUiState
}