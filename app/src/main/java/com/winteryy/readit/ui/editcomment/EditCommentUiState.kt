package com.winteryy.readit.ui.editcomment

import com.winteryy.readit.model.Comment

data class EditCommentUiState(
    val isEditing: Boolean = false,
    val comment: Comment? = null,
    val editingText: String = ""
)
