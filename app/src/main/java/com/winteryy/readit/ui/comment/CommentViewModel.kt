package com.winteryy.readit.ui.comment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.winteryy.readit.data.Result
import com.winteryy.readit.data.local.commentstorage.CommentStorageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommentViewModel @Inject constructor(
    private val commentStorageRepository: CommentStorageRepository
) : ViewModel() {
    private val _commentUiState: MutableStateFlow<CommentUiState> = MutableStateFlow(CommentUiState.Loading)
    val commentUiState: StateFlow<CommentUiState> get() = _commentUiState.asStateFlow()

    private var currentJob: Job? = null

    init {
        setCommentMainState()
    }

    fun setCommentMainState() {
        currentJob?.cancel()

//        currentJob = viewModelScope.launch {
//            commentStorageRepository.getRecentComments()
//                .combine(commentStorageRepository.getCommentNum()) { recentCommentList, commentNum ->
//                    if (recentCommentList is Result.Success && commentNum is Result.Success) {
//                        _commentUiState.value = CommentUiState.CommentMainState(
//                            commentNum.data,
//                            recentCommentList.data
//                        )
//                    } else {
//                        _commentUiState.value = CommentUiState.FailState
//                    }
//                }
//        }
    }

    fun setCommentListState() {
        currentJob?.cancel()

        viewModelScope.launch {
            when(val result = commentStorageRepository.getCommentsWithBooksPagingFlow()) {
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