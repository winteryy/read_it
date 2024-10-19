package com.winteryy.readit.ui.editcomment

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.SnackbarHostState
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
import com.winteryy.readit.ui.components.IndeterminateCircularIndicator
import com.winteryy.readit.ui.components.TextTopBar
import com.winteryy.readit.ui.components.dialog.CustomDialog
import com.winteryy.readit.ui.components.dialog.DialogButtonInfo
import com.winteryy.readit.ui.components.dialog.DialogButtonType
import com.winteryy.readit.ui.theme.ReadItTheme
import com.winteryy.readit.ui.theme.Typography
import com.winteryy.readit.ui.theme.theme_grey_black

@Composable
fun EditCommentScreen(
    isbn: String,
    snackbarHostState: SnackbarHostState,
    onBackArrowClicked: () -> Unit,
    modifier: Modifier = Modifier,
    editCommentViewModel: EditCommentViewModel = hiltViewModel()
) {
    //todo 한 번 클릭했을 때 바로 편집모드로 안 바뀌는 문제
    //todo 네비게이션바 숨김 처리
    //todo 바로 나가기 말고 다이얼로그 처리 필요
    val editCommentUiState by editCommentViewModel.uiState.collectAsStateWithLifecycle()
    val focusManager = LocalFocusManager.current

    LaunchedEffect(isbn) {
        editCommentViewModel.initState(isbn)
    }

    LaunchedEffect(Unit) {
        editCommentViewModel.deleteCompletionEventFlow.collect {
            onBackArrowClicked()
        }
    }

    BackHandler {
        if(editCommentUiState.isEditing) {
            editCommentViewModel.showDialog()
        } else {
            onBackArrowClicked()
        }
    }

    Box(
        modifier = modifier
    ) {
        if (editCommentUiState.isEditing.not()) {
            focusManager.clearFocus()
        }

        LaunchedEffect(editCommentUiState.errorMessage) {
            editCommentUiState.errorMessage?.let {
                snackbarHostState.showSnackbar(it)
                editCommentViewModel.consumeErrorMessage()
            }
        }

        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            TextTopBar(
                title = "코멘트 작성",
                onBackArrowClicked = if(editCommentUiState.isEditing) ( {editCommentViewModel.showDialog()} ) else onBackArrowClicked,
                backButtonImageVector = Icons.Filled.Close,
                trailingText = if (editCommentUiState.isEditing) "완료" else "삭제",
                trailingTextCallback = {
                    if (editCommentUiState.isEditing) {
                        editCommentViewModel.saveComment()
                    } else {
                        editCommentViewModel.showDialog()
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
                        if (focusState.isFocused) {
                            editCommentViewModel.toggleEditing()
                        }
                    },
            )
        }
    }

    if(editCommentUiState.showDialog) {
        if(editCommentUiState.isEditing) {
            CustomDialog(
                description = "저장하지 않은 변경사항은 반영되지 않습니다.\n나가시겠습니까?",
                buttons = listOf(
                    DialogButtonInfo(
                        text = "확인",
                        type = DialogButtonType.FILLED,
                    ) { onBackArrowClicked() },
                    DialogButtonInfo(
                        text = "취소",
                        type = DialogButtonType.OUTLINED
                    ) { editCommentViewModel.hideDialog() }
                )
            )
        } else {
            CustomDialog(
                description = "코멘트를 삭제하시겠습니까?",
                buttons = listOf(
                    DialogButtonInfo(
                        text = "확인",
                        type = DialogButtonType.FILLED,
                    ) { editCommentViewModel.deleteComment() },
                    DialogButtonInfo(
                        text = "취소",
                        type = DialogButtonType.OUTLINED
                    ) { editCommentViewModel.hideDialog() }
                )
            )
        }
    }

    if (editCommentUiState.isLoading) {
        IndeterminateCircularIndicator(
            modifier = Modifier
                .fillMaxSize()
                .background(theme_grey_black.copy(alpha = 0.5f))
        )
    }
}

@Preview(showBackground = true)
@Composable
fun EditCommentScreenPreview() {
    ReadItTheme {
        EditCommentScreen("", SnackbarHostState(), {})
    }
}