package com.winteryy.readit.ui.editcomment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.winteryy.readit.data.LocalError
import com.winteryy.readit.data.Result
import com.winteryy.readit.data.local.commentstorage.CommentStorageRepository
import com.winteryy.readit.model.Comment
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class EditCommentViewModel @Inject constructor(
    private val commentStorageRepository: CommentStorageRepository
): ViewModel() {
    private val _uiState: MutableStateFlow<EditCommentUiState> = MutableStateFlow(EditCommentUiState(isLoading = true))
    val uiState: StateFlow<EditCommentUiState> get() = _uiState.asStateFlow()

    private val _deleteCompletionEventFlow: MutableSharedFlow<Unit> = MutableSharedFlow()
    val deleteCompletionEventFlow: SharedFlow<Unit> get() = _deleteCompletionEventFlow.asSharedFlow()

    private var curIsbn: String = ""
    private var curJob: Job? = null

    fun initState(isbn: String) {
        curJob = viewModelScope.launch {
            curIsbn = isbn

            commentStorageRepository.getCommentByIsbn(curIsbn).collectLatest { result ->
                when(result) {
                    is Result.Error -> {
                        if(result.exception is LocalError.NoMatchItemError) {
                            _uiState.value = EditCommentUiState(
                                isLoading = false,
                                isEditing = true,
                                comment = null,
                                editingText = ""
                            )
                        } else {
                            _uiState.value = EditCommentUiState(
                                isLoading = false,
                                errorMessage = result.exception.message
                            )
                        }
                    }
                    is Result.Success -> {
                        _uiState.value = EditCommentUiState(
                            isLoading = false,
                            isEditing = false,
                            comment = result.data,
                            editingText = result.data.content
                        )
                    }
                }
            }
        }
    }

    fun updateEditingText(text: String) {
        _uiState.value = _uiState.value.copy(
            editingText = text
        )
    }

    fun toggleEditing() {
        _uiState.value = _uiState.value.copy(
            isEditing = !_uiState.value.isEditing,
            editingText = _uiState.value.comment?.content?: ""
        )
    }

    fun saveComment() = viewModelScope.launch {
        _uiState.value = _uiState.value.copy(
            isLoading = true
        )

        val result = commentStorageRepository.insertComment(
            comment = _uiState.value.comment?.copy(
                content = _uiState.value.editingText,
                updateDate = Date()
            ) ?: Comment(
                id = 0,
                content = _uiState.value.editingText,
                updateDate = Date(),
                bookIsbn = curIsbn
            )
        )

        when(result) {
            is Result.Error -> {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = result.exception.message
                )
            }
            is Result.Success -> {
                _uiState.value = _uiState.value.copy(
                    isLoading = false
                )
            }
        }

    }

    fun deleteComment() = viewModelScope.launch {
        _uiState.value =  _uiState.value.copy(
            isLoading = true
        )

        _uiState.value.comment?.let { comment ->
            when(val result = commentStorageRepository.deleteCommentById(comment.id)) {
                is Result.Error -> {
                    _uiState.value =  _uiState.value.copy(
                        isLoading = false,
                        errorMessage = result.exception.message
                    )
                }
                is Result.Success -> {
                    curJob?.cancel()
                    _deleteCompletionEventFlow.emit(Unit)
                }
            }
        }
    }

    fun consumeErrorMessage() {
        _uiState.value = _uiState.value.copy(
            errorMessage = null
        )
    }
}