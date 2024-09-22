package com.winteryy.readit.ui.comment

import androidx.paging.PagingData
import com.winteryy.readit.model.Comment
import kotlinx.coroutines.flow.Flow

sealed interface CommentUiState {
    object Loading: CommentUiState
    data class CommentMainState(
        val commentNum: Int,
        val recentCommentList: List<Comment>
    ): CommentUiState
    data class CommentListState(
        val commentPagingDataFlow: Flow<PagingData<Comment>>
    ): CommentUiState
}