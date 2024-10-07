package com.winteryy.readit.ui.editcomment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.winteryy.readit.data.LocalError
import com.winteryy.readit.data.Result
import com.winteryy.readit.data.local.commentstorage.CommentStorageRepository
import com.winteryy.readit.model.Comment
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class EditCommentViewModel @Inject constructor(
    private val commentStorageRepository: CommentStorageRepository
): ViewModel() {
    private val _uiState = MutableStateFlow(EditCommentUiState())
    val uiState: StateFlow<EditCommentUiState> get() = _uiState.asStateFlow()

    private var curIsbn: String = ""

    fun initState(isbn: String) = viewModelScope.launch {
        curIsbn = isbn
        commentStorageRepository.getCommentByIsbn(curIsbn).collectLatest { result ->
            when(result) {
                is Result.Error -> {
                    if(result.exception is LocalError.NoMatchItemError) {
                        _uiState.value = EditCommentUiState(
                            isEditing = true,
                            comment = null,
                            editingText = ""
                        )
                    } else {
                        //todo() 예외처리
                    }
                }
                is Result.Success -> {
                    _uiState.value = EditCommentUiState(
                        isEditing = false,
                        comment = result.data,
                        editingText = result.data.content
                    )
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
        commentStorageRepository.insertComment(
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
    }

}