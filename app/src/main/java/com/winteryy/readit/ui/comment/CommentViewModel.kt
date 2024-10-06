package com.winteryy.readit.ui.comment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.winteryy.readit.data.Result
import com.winteryy.readit.data.local.bookstorage.BookStorageRepository
import com.winteryy.readit.data.local.commentstorage.CommentStorageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommentViewModel @Inject constructor(
    private val bookStorageRepository: BookStorageRepository,
    private val commentStorageRepository: CommentStorageRepository
) : ViewModel() {
    private val _commentUiState: MutableStateFlow<CommentUiState> =
        MutableStateFlow(CommentUiState.Loading)
    val commentUiState: StateFlow<CommentUiState> get() = _commentUiState.asStateFlow()

    private var currentJob: Job? = null

    init {
        setCommentMainState()
    }

    fun setCommentMainState() {
        currentJob?.cancel()

        currentJob = viewModelScope.launch {
            commentStorageRepository.getRecentComments()
                .combine(commentStorageRepository.getCommentNum()) { recentCommentWithBookList, commentNum ->
                    if (recentCommentWithBookList is Result.Success && commentNum is Result.Success) {
                        CommentUiState.CommentMainState(
                            commentNum.data,
                            recentCommentWithBookList.data
                        )
                    } else {
                        CommentUiState.FailState
                    }
                }
                .collectLatest { state ->
                    _commentUiState.value = state
                }
        }
    }

    fun setCommentListState() {
        currentJob?.cancel()

        viewModelScope.launch {
            when (val result = bookStorageRepository.getBooksHavingCommentPagingFlow()) {
                is Result.Error -> {
                    //todo 에러 핸들링
                }

                is Result.Success -> {
                    _commentUiState.value = CommentUiState.CommentListState(
                        result.data
                    )
                }
            }
        }
    }
}