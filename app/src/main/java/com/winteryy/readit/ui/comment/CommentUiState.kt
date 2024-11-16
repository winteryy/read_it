package com.winteryy.readit.ui.comment

import androidx.paging.PagingData
import com.winteryy.readit.model.Book
import com.winteryy.readit.model.Comment
import kotlinx.coroutines.flow.Flow

sealed interface CommentUiState {
    val isLoading: Boolean
    val errorMessage: String?

    data class CommentMainState(
        val commentNum: Int = 0,
        val recentCommentWithBookList: List<Pair<Comment, Book>> = emptyList(),
        val curPage: Int = 0,
        override val isLoading: Boolean = false,
        override val errorMessage: String? = null
    ): CommentUiState

    data class CommentListState(
        val booksHavingCommentPagingDataFlow: Flow<PagingData<Book>>,
        override val isLoading: Boolean = false,
        override val errorMessage: String? = null
    ): CommentUiState

}