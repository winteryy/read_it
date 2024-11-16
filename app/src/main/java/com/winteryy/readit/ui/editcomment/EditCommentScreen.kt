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
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.winteryy.readit.R
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
    val editCommentUiState by editCommentViewModel.uiState.collectAsStateWithLifecycle()
    val focusManager = LocalFocusManager.current
    val textFieldFocusRequester = remember { FocusRequester() }

    LaunchedEffect(isbn) {
        editCommentViewModel.initState(isbn)
    }

    LaunchedEffect(Unit) {
        editCommentViewModel.deleteCompletionEventFlow.collect {
            editCommentViewModel.hideDialog()
            onBackArrowClicked()
        }
    }

    if(editCommentUiState.isEditing) {
        LaunchedEffect(Unit) {
            textFieldFocusRequester.requestFocus()
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
                title = stringResource(R.string.edit_comment_title),
                onBackArrowClicked = if(editCommentUiState.isEditing) ( {editCommentViewModel.showDialog()} ) else onBackArrowClicked,
                backButtonImageVector = Icons.Filled.Close,
                trailingText = if (editCommentUiState.isEditing) stringResource(R.string.complete)
                    else stringResource(R.string.delete),
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
                placeholder = { Text(text = stringResource(R.string.edit_comment_text_field_placeholder)) },
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth()
                    .focusRequester(textFieldFocusRequester)
                    .onFocusChanged { focusState ->
                        if (focusState.isFocused && !editCommentUiState.isEditing) {
                            editCommentViewModel.toggleEditing()
                        }
                    },
            )
        }
    }

    if(editCommentUiState.showDialog) {
        if(editCommentUiState.isEditing) {
            CustomDialog(
                description = stringResource(R.string.dialog_description_warn_non_save),
                buttons = listOf(
                    DialogButtonInfo(
                        text = stringResource(R.string.confirm),
                        type = DialogButtonType.FILLED,
                    ) {
                        editCommentViewModel.hideDialog()
                        onBackArrowClicked()
                      },
                    DialogButtonInfo(
                        text = stringResource(R.string.cancel),
                        type = DialogButtonType.OUTLINED
                    ) { editCommentViewModel.hideDialog() }
                )
            )
        } else {
            CustomDialog(
                description = stringResource(R.string.dialog_description_warn_deletion),
                buttons = listOf(
                    DialogButtonInfo(
                        text = stringResource(R.string.confirm),
                        type = DialogButtonType.FILLED,
                    ) { editCommentViewModel.deleteComment() },
                    DialogButtonInfo(
                        text = stringResource(R.string.cancel),
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