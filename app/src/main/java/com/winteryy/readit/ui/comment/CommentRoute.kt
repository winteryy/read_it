package com.winteryy.readit.ui.comment

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun CommentRoute(
    snackbarHostState: SnackbarHostState,
    navigateToEditComment: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val commentViewModel: CommentViewModel = hiltViewModel()
    val commentUiState = commentViewModel.commentUiState.collectAsStateWithLifecycle()

    commentUiState.value.let { curState ->
        when(curState) {
            is CommentUiState.CommentListState -> {
                CommentListScreen(
                    commentListState = curState,
                    navigateToCommentMain = { commentViewModel.setCommentMainState() },
                    onCommentItemClicked = { navigateToEditComment(it.isbn) },
                    modifier = modifier
                )
            }
            is CommentUiState.CommentMainState -> {
                CommentMainScreen(
                    commentMainState = curState,
                    snackbarHostState = snackbarHostState,
                    consumeErrorMessage = { commentViewModel.consumeErrorMessage() },
                    navigateToCommentList = { commentViewModel.setCommentListState() },
                    onCommentItemClicked = { navigateToEditComment(it) },
                    onPageChanged = { commentViewModel.updateCurPage(it) },
                    modifier = modifier,
                )
            }
        }
    }

}