package com.winteryy.readit.ui.comment

import androidx.paging.PagingData
import com.winteryy.readit.model.Book
import com.winteryy.readit.model.Comment
import kotlinx.coroutines.flow.Flow

sealed interface CommentUiState {
    object Loading: CommentUiState
    object FailState: CommentUiState
    data class CommentMainState(
        val commentNum: Int,
        val recentCommentWithBookList: List<Pair<Comment, Book>>,
        val curPage: Int = 0
    ): CommentUiState
    data class CommentListState(
        val booksHavingCommentPagingDataFlow: Flow<PagingData<Book>>
    ): CommentUiState

}