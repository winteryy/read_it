package com.winteryy.readit.ui.editcomment

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.winteryy.readit.ui.components.TextTopBar
import com.winteryy.readit.ui.theme.ReadItTheme
import com.winteryy.readit.ui.theme.Typography

@Composable
fun EditCommentScreen(
    isbn: String,
    onBackArrowClicked: () -> Unit,
    modifier: Modifier = Modifier
) {

    //todo 한 번 클릭했을 때 바로 편집모드로 안 바뀌는 문제
    val editCommentViewModel: EditCommentViewModel = hiltViewModel()
    val editCommentUiState by editCommentViewModel.uiState.collectAsStateWithLifecycle()
    val focusManager = LocalFocusManager.current

    LaunchedEffect(Unit) {
        editCommentViewModel.initState(isbn)
    }

    if(editCommentUiState.isEditing.not()) {
        focusManager.clearFocus()
    }

    Column(
        modifier = modifier
    ) {
        TextTopBar(
            title = "코멘트 작성",
            onBackArrowClicked = onBackArrowClicked,
            backButtonImageVector = Icons.Filled.Close,
            trailingText = if(editCommentUiState.isEditing) "완료" else "삭제",
            trailingTextCallback = {
                if(editCommentUiState.isEditing) {
                    editCommentViewModel.saveComment()
                } else {
                    editCommentViewModel.deleteComment()
                    onBackArrowClicked()
                }
            }
        )

        HorizontalDivider()

        TextField(
            value = editCommentUiState.editingText,
            textStyle = Typography.bodyMedium,
            onValueChange = { editCommentViewModel.updateEditingText(it) },
            colors = TextFieldDefaults.colors().copy(
                unfocusedContainerColor = Color.White
            ),
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .onFocusChanged { focusState ->
                    if(focusState.isFocused) {
                        editCommentViewModel.toggleEditing()
                    }
                },
        )
    }

}

@Preview(showBackground = true)
@Composable
fun EditCommentScreenPreview() {
    ReadItTheme {
        EditCommentScreen("", {})
    }
}